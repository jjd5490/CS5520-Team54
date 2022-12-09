package com.example.team54;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OpponentSelection extends AppCompatActivity {

    ArrayList<String> users;
    LinearLayout userDisplay;
    String username;
    FirebaseDatabase db;
    List<WCGameModel> openGames;
    List<String> gameRefs;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_selection);
        openGames = new ArrayList<>();
        gameRefs = new ArrayList<>();

        recyclerView = findViewById(R.id.game_select_recyclerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        users = (ArrayList<String>) getIntent().getSerializableExtra("UserList");
        username = getIntent().getStringExtra("Username");

        users.remove(username);

        db = FirebaseDatabase.getInstance();

        fetchGames();
    }

    public void startGame(View view) {
        Intent intent = new Intent(OpponentSelection.this, GamePlayActivity.class);
        startActivity(intent);
    }

    public void startParty(View view) {
        WCGameModel game = new WCGameModel(
                "_", username, false, true, 0,
                new UserDataModel(),
                new UserDataModel()
        );
        long time = System.currentTimeMillis();
        db.getReference("Games").child(String.valueOf(time)).setValue(game);
        // open game and wait for p2
    }

    public void fetchGames() {
        db.getReference("Games").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> rawGames = task.getResult().getChildren();
                    for (DataSnapshot d : rawGames) {
                        Log.d("************* ", d.getKey());
                        WCGameModel g = d.getValue(WCGameModel.class);
                        if (g != null) {
                            if (g.getGuest().equals("_")) {
                                openGames.add(g);
                                gameRefs.add(d.getKey());
                            }
                        }
                    }

                    if (openGames.size() > 0) {
                        adapter = new WCGameModelAdapter(openGames, OpponentSelection.this,
                                gameRefs, db, username);
                        recyclerView.setAdapter(adapter);
                    }
            }
        });
    }
}