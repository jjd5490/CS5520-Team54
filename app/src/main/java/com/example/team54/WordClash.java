package com.example.team54;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class WordClash extends AppCompatActivity {

    ArrayList<String> users;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_clash);
        users = (ArrayList<String>) getIntent().getSerializableExtra("UserList");
        username = getIntent().getStringExtra("Username");
        if (users != null) {
            Log.d("******* Users from WordClash activity", users.toString());
        }
    }

    public void openGame(View view) {
        Intent intent = new Intent(this, OpponentSelection.class);
        intent.putExtra("UserList", users);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    public void openRules(View view) {
        Intent rules = new Intent(this, RulesActivity.class);
        startActivity(rules);
    }

    public void openScores(View view) {
        Intent scores = new Intent(this, HighScoresActivity.class);
        startActivity(scores);
    }

    public void openSettings(View view) {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }
}