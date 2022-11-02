package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView signUpUsername;
    private TextView loginUsername;
    private final String DEFAULT_PASSWORD = "default";
    private final String TAG = "Login Activity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        auth = FirebaseAuth.getInstance();
        //signUpUsername = findViewById(R.id.);
        loginUsername = findViewById(R.id.login_username);
    }

    public void onSignUpSubmit(View view) {
        String username = (String) signUpUsername.getText();
        auth.createUserWithEmailAndPassword(username, DEFAULT_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Sign up successful");
                        } else {
                            Log.d(TAG, "Sign up failed");
                        }
                    }
                });
    }

    public void signIn(View view) {
        String username = (String) loginUsername.getText();
        auth.signInWithEmailAndPassword(username, DEFAULT_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "sign in successful");
                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                            Log.d(TAG, "sign in failed");
                        }
                    }
                });
    }
}