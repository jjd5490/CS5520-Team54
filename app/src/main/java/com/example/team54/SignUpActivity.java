package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView signUpEmail;
    private TextView signUpName;
    private final String DEFAULT_PASSWORD = "default";
    private final String TAG = "Sign-up Activity: ";
    private FirebaseDatabase db;
    private String UID;
    List<String> test;
    List<MessageModel> sampleMessageList;
    String deviceToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        signUpEmail = findViewById(R.id.email_signup_input);
        signUpName = findViewById(R.id.name_signup_input);
        db = FirebaseDatabase.getInstance();
        test = new ArrayList<>();
        test.add("Liz");
        test.add("Joey");
        sampleMessageList = new ArrayList<>();
        sampleMessageList.add(new MessageModel("Team54", "UID", "2131165334", String.valueOf(System.currentTimeMillis())));
    }

    public void onSubmit(View view) {
        String email = signUpEmail.getText().toString();
        String name = signUpName.getText().toString();
        auth.createUserWithEmailAndPassword(email, DEFAULT_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Sign up successful");
                            postUserToDB(email, name);
                            Bundle userData = new Bundle();
                            userData.putString("UID", UID);
                            Intent stickerIntent = new Intent(getApplicationContext(), StickerActivity.class);
                            stickerIntent.putExtras(userData);
                            startActivity(stickerIntent);
                        } else {
                            Log.d(TAG, "Sign up failed");
                        }
                    }
                });
    }


    public void postUserToDB(String email, String name) {
        FirebaseUser user = auth.getCurrentUser();
        UID = user.getUid();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    deviceToken = task.getResult();
                    if (user != null) {
                        //UserModel userData = new UserModel(email, UID, name, deviceToken, test, sampleMessageList, sampleMessageList, new InboxModel("Team54", "2131165334", String.valueOf(System.currentTimeMillis())));
                        UserModel userData = new UserModel(email,
                                UID,
                                name,
                                deviceToken,
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new InboxModel());
                        db.getReference().child("Users").child(UID).setValue(userData);
                    }
                }
            }
        });
    }
}