package com.example.team54;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WordClash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_clash);
    }

    public void openGame(View view) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        startActivity(intent);
    }
}