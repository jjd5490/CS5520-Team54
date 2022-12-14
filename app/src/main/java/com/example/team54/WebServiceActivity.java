package com.example.team54;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceActivity extends AppCompatActivity {
    private static final String TAG = "WebServiceActivity";

    private Retrofit retrofit;
    private Button testCall;
    private EndpointHelper api;
    private final String apiBaseURL = "https://www.balldontlie.io/api/v1/";
    private RecyclerView gameListRecyclerView;
    private Spinner teamDropDown;
    private Spinner seasonDropDown;
    private RadioGroup gameTypeSelection;
    private Context context = this;
    List<GameModel> Games = new ArrayList<>();
    TeamModelAdapter gameAdapter;
    List<Integer> SEASONS;
    ProgressBar loadAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        createSeasonList();

        gameAdapter = new TeamModelAdapter(Games, getApplicationContext());
        gameListRecyclerView = findViewById(R.id.recyclerView);
        gameListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        gameListRecyclerView.setAdapter(gameAdapter);

        teamDropDown = findViewById(R.id.spinner);
        seasonDropDown = findViewById(R.id.season_dropdown);
        gameTypeSelection = findViewById(R.id.postseason_selection);
        loadAnimation = findViewById(R.id.loading);
        gameTypeSelection.check(R.id.all_games);

        TeamNames[] teamList = TeamNames.values();
        //ArrayAdapter<TeamNames> teamAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
        //teamDropDown.setAdapter(teamAdapter);

        //ArrayAdapter<Integer> seasonAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, SEASONS);
        //seasonDropDown.setAdapter(seasonAdapter);

        testCall = findViewById(R.id.test_call);
        testCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testCall.setClickable(false);
                loadAnimation.setVisibility(View.VISIBLE);
                getGames();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(EndpointHelper.class);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TeamNames team = (TeamNames) teamDropDown.getSelectedItem();
        int teamID = team.ordinal() + 1;
        String seasonID = seasonDropDown.getSelectedItem().toString();
        outState.putInt("team", teamID);
        outState.putString("season", seasonID);
        GameModel[] gameOut = GameModel.CREATOR.newArray(Games.size());
        for (int i = 0; i < Games.size(); i++) {
            gameOut[i] = Games.get(i);
        }
        outState.putParcelableArray("games", gameOut);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int teamId = savedInstanceState.getInt("team");
        int seasonId = parseInt(savedInstanceState.getString("season"));
        int seasonIndex = (2021 - seasonId);
        Parcelable[] parcelableIn = savedInstanceState.getParcelableArray("games");
        List<GameModel> gamesIn = new ArrayList<>();
        for (Parcelable game : parcelableIn) {
            gamesIn.add( (GameModel) game);
        }
        teamDropDown.setSelection(teamId - 1);
        seasonDropDown.setSelection(seasonIndex);
        Games = gamesIn;
        gameAdapter.setGameList(Games);

    }


    private void getGames() {
        gameAdapter.setGameList(new ArrayList<>());
        TeamNames selectedTeam = (TeamNames) teamDropDown.getSelectedItem();
        int teamID = (selectedTeam.ordinal() + 1);
        int selectedSeason = (int) seasonDropDown.getSelectedItem();
        RadioButton selectedButton = findViewById(gameTypeSelection.getCheckedRadioButtonId());
        String selectedType = (String) selectedButton.getText();

        Call<GameResponseModel> call = null;
        switch (selectedType) {
            case "Playoffs":
                call = api.getGamesPostFilter(teamID, selectedSeason, true, 100);
                break;
            case "Regular":
                call = api.getGamesPostFilter(teamID, selectedSeason, false, 100);
                break;
            case "All":
                call = api.getAllSeasonGames(teamID, selectedSeason, 100);
                break;
        }
        call.enqueue(new Callback<GameResponseModel>() {
            @Override
            public void onResponse(Call<GameResponseModel> call, Response<GameResponseModel> response) {

                if (response.code() == 200) {
                    Log.d(TAG, "Response code is: " + response.code());
                    List<GameModel> gameList = response.body().getGameList();
                    Games = gameList;
                    gameAdapter.setGameList(Games);
                    if (Games.size() == 0) {
                        Toast.makeText(context, "No results matching search criteria", Toast.LENGTH_LONG).show();
                    }
                }
                loadAnimation.setVisibility(View.INVISIBLE);
                testCall.setClickable(true);
                if (response.code() == 429) {
                    Toast.makeText(context, "Rate Limit Exceeded. Wait 60 sec.", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 500 || response.code() == 503) {
                    Toast.makeText(context, "Server Unavailable. Try later", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GameResponseModel> call, Throwable t) {
                Log.d(TAG, "Call failed: " + t.getMessage());
            }
        });
    }

    public void createSeasonList() {
        List<Integer> seasons = new ArrayList<>();
        for (int i = 2021; i > 1978; i--) {
            seasons.add(i);
        }
        SEASONS = seasons;
    }
}

