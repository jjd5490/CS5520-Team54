package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


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
    private DatabaseReference usersRef;
    private String userId, emojiSelected, receiverId;
    private Integer contactPositionSelected;
    private Spinner emojiSpinner, contactSpinner;
    private List<String> contactsName, contactsID;
    private HashMap<String, Integer> resourceIndex;
    private Integer emojiSelId;
    private List<UserModel> usersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        if(getIntent().getExtras() != null) {
            userId = getIntent().getExtras().getString("UID");
        }
        Log.d(TAG, "UID: " + userId);

        db = FirebaseDatabase.getInstance();
        usersRef = db.getReference("Users");
        contactsName = new ArrayList<>();
        contactsID = new ArrayList<>();
        contactsName.add("Select ...");
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


        ArrayAdapter<CharSequence> adapterEmoji = ArrayAdapter.createFromResource(this,
                R.array.emoji_names, android.R.layout.simple_spinner_item);
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

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersData = new ArrayList<UserModel>();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                    UserModel user = datasnapshot.getValue(UserModel.class);
                    usersData.add(user);
                }
                for (UserModel user : usersData) {
                    contactsID.add(user.getUID());
                    contactsName.add(user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> adapterContacts = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, contactsName);
        adapterContacts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contactSpinner.setAdapter(adapterContacts);
        contactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    contactPositionSelected = i-1;
                    Log.d(TAG, "Contact Position: **********" + contactPositionSelected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void sendMessageToContact(View view) {
        receiverId = contactsID.get(contactPositionSelected);
        String strInfo = "Sender: " + userId + " // Receiver: " + receiverId +
            " // Emoji: " + emojiSelected + " // Emoji Id: " + emojiSelId;
        Log.d(TAG, strInfo);
    }
}

