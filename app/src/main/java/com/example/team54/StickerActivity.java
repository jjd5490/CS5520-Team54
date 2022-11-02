package com.example.team54;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StickerActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        userID = getIntent().getExtras().getString("UID");
        Log.d("Sticker activity: ", "userID = " + userID);
        db = FirebaseDatabase.getInstance();
    }

    public void addDataTest(View view) {
        DatabaseReference ref = db.getReference("Users");
        ref.child(userID).setValue(new UserModel("jamesdudek5@gmail.com", userID, "James"));
    }
}