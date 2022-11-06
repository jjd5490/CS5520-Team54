package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;


public class LoginTest extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView signUpUsername;
    private TextView loginUsername;
    private final String DEFAULT_PASSWORD = "default";
    private final String TAG = "Login Activity: ";
    private String UID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        auth = FirebaseAuth.getInstance();
        //signUpUsername = findViewById(R.id.);
        loginUsername = findViewById(R.id.login_username);
    }

    public void openSignUp(View view) {
        Intent openSignUpActivity = new Intent(this.getApplicationContext(), SignUpActivity.class);
        startActivity(openSignUpActivity);
    }

    public void signIn(View view) {
        String username = loginUsername.getText().toString();
        auth.signInWithEmailAndPassword(username, DEFAULT_PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "sign in successful");
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                UID = user.getUid();
                            }
                            Bundle userData = new Bundle();
                            userData.putString("UID", UID);
                            Intent stickerIntent = new Intent(getApplicationContext(), StickerActivity.class);
                            stickerIntent.putExtras(userData);
                            startActivity(stickerIntent);

                        } else {
                            Log.d(TAG, task.getException().toString());
                            Log.d(TAG, "sign in failed");

                        }
                    }
                });
    }
}