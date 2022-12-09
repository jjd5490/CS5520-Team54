package com.example.team54;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class WCGameModelAdapter extends RecyclerView.Adapter<WCGameModelViewHolder> {

    private List<WCGameModel> gameList;
    private final Context context;
    List<String> gameRefs;
    FirebaseDatabase db;
    String username;

    public WCGameModelAdapter(List<WCGameModel> gameList, Context context, List<String> gameRefs,
                              FirebaseDatabase db, String username) {
        this.gameList = gameList;
        this.context = context;
        this.gameRefs = gameRefs;
        this.db = db;
        this.username = username;
    }

    @NonNull
    @Override
    public WCGameModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WCGameModelViewHolder(LayoutInflater.from(context).inflate(R.layout.select_game_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull WCGameModelViewHolder holder, int position) {
        holder.user.setText(gameList.get(position).getHost());
        holder.setGameKey(gameRefs.get(position));
        holder.setDb(db);
        holder.setUsername(username);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
