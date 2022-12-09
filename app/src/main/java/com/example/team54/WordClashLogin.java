package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class WordClashLogin extends AppCompatActivity {

    private FirebaseDatabase db;
    public String username;
    private TextView un;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_clash_login);

        un = findViewById(R.id.wordclash_username_input);
        db = FirebaseDatabase.getInstance();
    }

    public void login(View view) {
        username = un.getText().toString();
        ArrayList<String> users = new ArrayList<>();

        db.getReference().child("WCUserList").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot result = task.getResult();
                    if (result != null) {
                        for (DataSnapshot u : result.getChildren()) {
                            String name = u.getValue(String.class);
                            users.add(name);
                        }
                        if (users.contains(username)) {
                            Log.d("********* Users from Login Activity:", users.toString());
                            Intent intent = new Intent(WordClashLogin.this, WordClash.class);
                            intent.putExtra("UserList", users);
                            intent.putExtra("Username", username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(WordClashLogin.this, "Username Not Found",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(WordClashLogin.this, "Network Error Occurred",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openSignup(View view) {
        Intent intent = new Intent(WordClashLogin.this, WordClashSignup.class);
        startActivity(intent);
    }
}