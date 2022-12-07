package com.example.team54;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openAbout(View view) {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    public void openWebServiceActivity(View view) {
        Intent webServiceIntent = new Intent(this, WebServiceActivity.class);
        startActivity(webServiceIntent);
    }

    public void openLoginActivity(View view) {
        Intent loginIntent = new Intent(this, LoginTest.class);
        startActivity(loginIntent);
    }

    public void openWordClashActivity(View view) {
        Intent wordClashIntent = new Intent(this, WordClash.class);
        startActivity(wordClashIntent);
    }

}