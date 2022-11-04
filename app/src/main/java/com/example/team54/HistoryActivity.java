package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private String userID;
    private UserModel user;
    private HashMap<String, Integer> resourceIndex;
    private Integer[] stickerCount = new Integer[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


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

        recyclerView = findViewById(R.id.receipt_history_recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

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
        resourceIndex.put("1", 0);
        resourceIndex.put("2", 1);
        resourceIndex.put("3", 2);
        resourceIndex.put("4", 3);
        resourceIndex.put("5", 4);
        resourceIndex.put("6", 5);
        resourceIndex.put("7", 6);
        resourceIndex.put("8", 7);
        resourceIndex.put("9", 8);
        resourceIndex.put("10", 9);
    }

    public void updateStats() {
        db.getReference().child("Users/" + userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                user = task.getResult().getValue(UserModel.class);
                adapter = new StickerRecyclerAdapter(user.getMessagesReceived(), getApplicationContext());
                recyclerView.setAdapter(adapter);

                for (MessageModel m : user.getMessagesSent()) {
                    stickerCount[resourceIndex.get(m.getResourceID())] += 1;
                }
                drawStats();
            }
        });
    }

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