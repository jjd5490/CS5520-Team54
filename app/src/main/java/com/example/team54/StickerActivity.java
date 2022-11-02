package com.example.team54;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class StickerActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private String userID;
    private TextView usernameDisplay;
    public UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        userID = getIntent().getExtras().getString("UID");
        Log.d("Sticker activity: ", "userID = " + userID);
        db = FirebaseDatabase.getInstance();
        usernameDisplay = findViewById(R.id.username_display);
        loadUser(new UserCallback() {
            @Override
            public void onGetUser(UserModel userObject) {
                user = userObject;
                String userDisplayText = "User: " + user.getName();
                usernameDisplay.setText(userDisplayText);
            }
        });
    }

    public void loadUser(UserCallback callback) {
        db.getReference().child("Users/" + userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                callback.onGetUser(task.getResult().getValue(UserModel.class));
            }
        });
    }
}