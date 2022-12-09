package com.example.team54;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class WCGameModelViewHolder extends RecyclerView.ViewHolder {

    public TextView user;
    public String gameKey;
    public FirebaseDatabase db;
    public String username;

    public WCGameModelViewHolder(@NonNull View itemView) {
        super(itemView);
        this.user = itemView.findViewById(R.id.opponent_name);
        this.gameKey = "";
        this.username = "";
        this.db = null;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Game Key = " + gameKey, Toast.LENGTH_LONG).show();
                db.getReference("Games").child(gameKey).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        WCGameModel game = currentData.getValue(WCGameModel.class);
                        game.setGuest(username);
                        currentData.setValue(game);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        if (committed) {
                            Intent intent = new Intent(itemView.getContext(), GamePlayActivity.class);
                            intent.putExtra("Username", username);
                            intent.putExtra("GameKey", gameKey);
                            itemView.getContext().startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    public void setGameKey(String key) {
        this.gameKey = key;
    }

    public void setDb(FirebaseDatabase db) {
        this.db = db;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
