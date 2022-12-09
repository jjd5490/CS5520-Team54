package com.example.team54;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;

public class WordClashSignup extends AppCompatActivity {

    private FirebaseDatabase db;
    public String username;
    private TextView un;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_clash_signup);

        un = findViewById(R.id.wc_username_input);
        db = FirebaseDatabase.getInstance();
    }

    public void signUp(View view) {
        username = un.getText().toString();
        ArrayList<String> users = new ArrayList<>();

        db.getReference().child("WCUserList").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot result = task.getResult();
                    if (result != null) {
                        long num = result.getChildrenCount();
                        for (DataSnapshot u : result.getChildren()) {
                            String name = u.getValue(String.class);
                            users.add(name);
                        }
                        if (!users.contains(username)) {
                            db.getReference().child("WCUserList").child(String.valueOf(num)).setValue(username);
                            Intent intent = new Intent(WordClashSignup.this, WordClash.class);
                            intent.putExtra("UserList", users);
                            intent.putExtra("Username", username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(WordClashSignup.this, "User Already Exists",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(WordClashSignup.this, "Network Error Occurred",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}