package com.example.team54;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    Spinner teamDropDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        teamDropDown = findViewById(R.id.spinner);
        //String[] teamList = new String[]{"Celtics", "Heat", "Spurs"};
        TeamNames[] teamList = TeamNames.values();
        ArrayAdapter<TeamNames> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamList);
        teamDropDown.setAdapter(adapter);

        testCall = findViewById(R.id.test_call);
        testCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "call button clicked");
                TeamNames selected = (TeamNames) teamDropDown.getSelectedItem();
                Log.d(TAG, "Selected Team is: " + (selected.ordinal() + 1));
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
        Call<GameResponseModel> call = api.getGameModels();
        call.enqueue(new Callback<GameResponseModel>() {
            @Override
            public void onResponse(Call<GameResponseModel> call, Response<GameResponseModel> response) {
                List<GameModel> gameList = response.body().getGameList();
                for(GameModel game : gameList) {
                    StringBuffer str = new StringBuffer();
                    str.append("id: " + game.getId());
                    str.append(" home: " + game.getHome_team().getName());
                    str.append(" visitor: " + game.getVisitor_team().getName());
                    Log.d(TAG, str.toString());
                    gameListRecyclerView = findViewById(R.id.recyclerView);
                    gameListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    gameListRecyclerView.setAdapter(new TeamModelAdapter(gameList, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<GameResponseModel> call, Throwable t) {
                Log.d(TAG, "Call failed: " + t.getMessage());
            }
        });
    }


}