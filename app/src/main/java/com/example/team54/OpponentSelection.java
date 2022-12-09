package com.example.team54;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OpponentSelection extends AppCompatActivity {

    ArrayList<String> users;
    LinearLayout userDisplay;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_selection);

        users = (ArrayList<String>) getIntent().getSerializableExtra("UserList");
        username = getIntent().getStringExtra("Username");

        users.remove(username);
        userDisplay = findViewById(R.id.opponent_list_layout);

        renderUserDisplay();
    }

    public void renderUserDisplay() {
        for (String u : users) {
            TextView t = new TextView(OpponentSelection.this);
            t.setText(u);
            t.setTextSize(24);
            userDisplay.addView(t);
        }
    }

    public void startGame(View view) {
        Intent intent = new Intent(OpponentSelection.this, GamePlayActivity.class);
        startActivity(intent);
    }
}