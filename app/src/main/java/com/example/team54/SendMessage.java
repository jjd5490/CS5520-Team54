package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendMessage extends AppCompatActivity {
    private static final String TAG ="SendMessageActivity";
    private FirebaseDatabase mDatabase;
    private DatabaseReference userRef;
    private TextView userIdTV;
    private String userId;
    private Spinner emojiSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        userIdTV = findViewById(R.id.textView2);
        emojiSpinner = findViewById(R.id.emoji_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.emoji_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emojiSpinner.setAdapter(adapter);

        if(getIntent().getExtras() != null) {
            userId = getIntent().getExtras().getString("UID");
//            userIdTV.setText(userId);
        }
        Log.d(TAG, "UID: " + userId);

        mDatabase = FirebaseDatabase.getInstance();
        userRef = mDatabase.getReference("Users");

//        userRef = FirebaseDatabase.getInstance().getReference().child("Users").
//                child(userId).child("contacts");
        Log.d(TAG, "Users contacts: " + userRef);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel users = dataSnapshot.getValue(UserModel.class);
                Log.d(TAG, "Users: " + users);
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    String ma = snapshot.child("contacts").getValue().toString();
                    //Log.d(TAG, "Users contacts: " + ma);
            }
//               String ma = dataSnapshot.getValue().toString();

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//    images





    }
}

//public class SendMessage extends Activity implements AdapterView.OnItemSelectedListener {
//    private static final String TAG ="SendMessageActivity";
//    public void onItemSelected(AdapterView<?>parent, View view, int pos, long id) {
//        int itemSelected = (int) parent.getItemAtPosition(pos);
//        Log.d(TAG, "sticker choosen: " + itemSelected);
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
//}