package com.example.team54;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class StickerActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private String userID;
    private TextView usernameDisplay;
    public UserModel user;
    public TextView resource_received_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        userID = getIntent().getExtras().getString("UID");
        db = FirebaseDatabase.getInstance();
        usernameDisplay = findViewById(R.id.username_display);
        resource_received_test = findViewById(R.id.test_resource_received);
        loadUser(new UserCallback() {
            @Override
            public void onGetUser(UserModel userObject) {
                user = userObject;
                String userDisplayText = "User: " + user.getName();
                usernameDisplay.setText(userDisplayText);
            }
        });

        db.getReference().child("Users/" + userID)
                .child("inbox")
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                onMessageReceived();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    public void onMessageReceived() {
        db.getReference().child("Users/" + userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String resourceID = task.getResult()
                        .getValue(UserModel.class)
                        .getInbox()
                        .getResourceID();
                resourceID = "Resource id: " + resourceID;
                resource_received_test.setText(resourceID);
            }
        });
    }

    public void testChildListener(View view) {
        db.getReference().child("Users/" + userID)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        UserModel user = currentData.getValue(UserModel.class);
                        if (user != null) {
                            user.receiveMessage(new MessageModel("Team54", userID, "1234"));
                            currentData.setValue(user);
                            return Transaction.success(currentData);
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    }
                });
    }

    public void openHistory(View view) {
        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        Bundle userData = new Bundle();
        userData.putString("UID", userID);
        historyIntent.putExtras(userData);
        startActivity(historyIntent);
    }
}