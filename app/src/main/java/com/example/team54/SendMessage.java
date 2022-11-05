package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendMessage extends AppCompatActivity {
    private static final String TAG ="SendMessageActivity";
    private FirebaseDatabase db;
    private DatabaseReference uniqueUserRef;
    private String userId, emojiSelected, contactSelected;
    private Spinner emojiSpinner, contactSpinner;
    private List<String> contacts;
    private HashMap<String, Integer> resourceIndex;
    private Integer emojiSelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        if(getIntent().getExtras() != null) {
            userId = getIntent().getExtras().getString("UID");
        }
        Log.d(TAG, "UID: " + userId);

        db = FirebaseDatabase.getInstance();
        uniqueUserRef = db.getReference().child("Users/"+userId+"/contacts");
        contacts = new ArrayList<>();
        contacts.add("Select ...");
        contactSpinner = findViewById(R.id.contact_spinner);
        emojiSpinner = findViewById(R.id.emoji_spinner);

        resourceIndex = new HashMap<>();
        resourceIndex.put("Angry", 0);
        resourceIndex.put("Funny", 1);
        resourceIndex.put("Glasses", 2);
        resourceIndex.put("Scream", 3);
        resourceIndex.put("Sick", 4);
        resourceIndex.put("Sleepy", 5);
        resourceIndex.put("Smile", 6);
        resourceIndex.put("Thinking", 7);
        resourceIndex.put("Unamused", 8);
        resourceIndex.put("Wink", 9);


        ArrayAdapter<CharSequence> adapterEmoji = ArrayAdapter.createFromResource(this, R.array.emoji_names, android.R.layout.simple_spinner_item);
        adapterEmoji.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emojiSpinner.setAdapter(adapterEmoji);
        emojiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    emojiSelected = (String) adapterView.getItemAtPosition(i);
                    emojiSelId = resourceIndex.get(emojiSelected);
                    Log.d(TAG,"Emoji: " + emojiSelected);
                    Log.d(TAG,"Emoji ID: " + emojiSelId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        uniqueUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                    if(datasnapshot.exists()){
                        contacts.add(datasnapshot.getValue().toString());
                    }
                }
                Log.d(TAG, "Contacts: " + contacts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        ArrayAdapter<String> adapterContacts = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, contacts);
        adapterContacts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactSpinner.setAdapter(adapterContacts);
        contactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    contactSelected = (String)adapterView.getItemAtPosition(i);
                    Log.d(TAG, "contact: " + contactSelected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void sendMessageToContact(View view) {
        String strInfo = "Sender: " + userId + " // Receiver: " + contactSelected +
                " // Emoji: " + emojiSelected + " // Emoji Id: " + emojiSelId;
        Log.d(TAG, strInfo);
    }
}

