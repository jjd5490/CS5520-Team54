package com.example.team54;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
    //private final String apiBaseURL = "https://jsonplaceholder.typicode.com/";
    private final String apiBaseURL = "https://www.balldontlie.io/api/v1/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        testCall = findViewById(R.id.test_call);
        testCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "call button clicked");
                getPosts();
                //getGames();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(EndpointHelper.class);
    }

    private void getPosts() {
        Call<List<PostModel>> call = api.getPostModels();
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "Call failed!" + response.code());
                    return;
                }
                List<PostModel> postModels = response.body();
                for(PostModel post : postModels) {
                    StringBuffer str = new StringBuffer();
                    str.append(post.getId());
                    Log.d(TAG, str.toString());
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Log.d(TAG, "Call failed" + t.getMessage());
            }
        });
    }

    /**
    private void getGames() {
        Call<List<GameModel>> call = api.getGameModels();
        call.enqueue(new Callback<List<GameModel>>() {
            @Override
            public void onResponse(Call<List<GameModel>> call, Response<List<GameModel>> response) {
                List<GameModel> gameModels = response.body();
                for(GameModel game : gameModels) {
                    StringBuffer str = new StringBuffer();
                    str.append(game.getHome_team());
                    str.append(game.getVisitor_score());
                    Log.d(TAG, str.toString());
                }
            }

            @Override
            public void onFailure(Call<List<GameModel>> call, Throwable t) {
                Log.d(TAG, "Call failed: " + t.getMessage());
            }
        });
    }
     **/
}