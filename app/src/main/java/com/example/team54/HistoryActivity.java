package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private String userID;
    private UserModel user;
    private HashMap<String, Integer> resourceIndex;
    private Integer[] stickerCount = new Integer[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };



    private TextView sticker1;
    private TextView sticker2;
    private TextView sticker3;
    private TextView sticker4;
    private TextView sticker5;
    private TextView sticker6;
    private TextView sticker7;
    private TextView sticker8;
    private TextView sticker9;
    private TextView sticker10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        resourceIndex = new HashMap<>();
        initializeHashMap();

        sticker1 = findViewById(R.id.sticker1_stats);
        sticker2 = findViewById(R.id.sticker2_stats);
        sticker3 = findViewById(R.id.sticker3_stats);
        sticker4 = findViewById(R.id.sticker4_stats);
        sticker5 = findViewById(R.id.sticker5_stats);
        sticker6 = findViewById(R.id.sticker6_stats);
        sticker7 = findViewById(R.id.sticker7_stats);
        sticker8 = findViewById(R.id.sticker8_stats);
        sticker9 = findViewById(R.id.sticker9_stats);
        sticker10 = findViewById(R.id.sticker10_stats);

        db = FirebaseDatabase.getInstance();
        userID = getIntent().getExtras().getString("UID");
        updateStats();


    }

    public void initializeHashMap() {
        resourceIndex.put(String.valueOf(R.drawable.angry), 0);
        resourceIndex.put(String.valueOf(R.drawable.funny), 1);
        resourceIndex.put(String.valueOf(R.drawable.glasses), 2);
        resourceIndex.put(String.valueOf(R.drawable.scream), 3);
        resourceIndex.put(String.valueOf(R.drawable.sick), 4);
        resourceIndex.put(String.valueOf(R.drawable.sleepy), 5);
        resourceIndex.put(String.valueOf(R.drawable.smile), 6);
        resourceIndex.put(String.valueOf(R.drawable.thinking), 7);
        resourceIndex.put(String.valueOf(R.drawable.unamused), 8);
        resourceIndex.put(String.valueOf(R.drawable.wink), 9);
    }

    public void updateStats() {
        db.getReference().child("Users/" + userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                user = task.getResult().getValue(UserModel.class);

                for (MessageModel m : user.getMessagesSent()) {
                    stickerCount[resourceIndex.get(m.getResourceID())] += 1;
                }
                drawStats();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void drawStats() {
        sticker1.setText(stickerCount[0].toString());
        sticker2.setText(stickerCount[1].toString());
        sticker3.setText(stickerCount[2].toString());
        sticker4.setText(stickerCount[3].toString());
        sticker5.setText(stickerCount[4].toString());
        sticker6.setText(stickerCount[5].toString());
        sticker7.setText(stickerCount[6].toString());
        sticker8.setText(stickerCount[7].toString());
        sticker9.setText(stickerCount[8].toString());
        sticker10.setText(stickerCount[9].toString());
    }
}