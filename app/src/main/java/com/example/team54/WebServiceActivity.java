package com.example.team54;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
    List<GameModel> Games = new ArrayList<>();
    TeamModelAdapter gameAdapter;
    List<Integer> SEASONS;


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
        gameTypeSelection.check(R.id.all_games);

        TeamNames[] teamList = TeamNames.values();
        ArrayAdapter<TeamNames> teamAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
        teamDropDown.setAdapter(teamAdapter);

        ArrayAdapter<Integer> seasonAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, SEASONS);
        seasonDropDown.setAdapter(seasonAdapter);

        testCall = findViewById(R.id.test_call);
        testCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.d(TAG,"Team id: " + teamID);
                //Log.d(TAG, "Season: " + selectedSeason);
                getGames();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(EndpointHelper.class);
    }


    private void getGames() {
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
                List<GameModel> gameList = response.body().getGameList();
                Log.d(TAG, "Total games = " + gameList.size());
                for(GameModel game : gameList) {
                    StringBuffer str = new StringBuffer();
                    str.append("id: " + game.getId());
                    str.append(" home: " + game.getHome_team().getName());
                    str.append(" visitor: " + game.getVisitor_team().getName());
                }
                gameAdapter.setGameList(gameList);
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

