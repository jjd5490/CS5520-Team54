package com.example.team54;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class TeamModelAdapter extends RecyclerView.Adapter<TeamModelViewHolder>{
    private static final String TAG = "Team Model Adapter";

    private List<GameModel> gameList;
    private final Context context;

    final private int[] images= {R.drawable.team1, R.drawable.team2, R.drawable.team3,
            R.drawable.team4, R.drawable.team5,R.drawable.team6, R.drawable.team7,R.drawable.team8,
            R.drawable.team9, R.drawable.team10,R.drawable.team11,R.drawable.team12,R.drawable.team13,
            R.drawable.team14, R.drawable.team15,R.drawable.team16, R.drawable.team17,R.drawable.team18,
            R.drawable.team19, R.drawable.team20,R.drawable.team21,R.drawable.team22,R.drawable.team23,
            R.drawable.team24, R.drawable.team25, R.drawable.team26,R.drawable.team27,
            R.drawable.team28, R.drawable.team29,R.drawable.team30 };


    public TeamModelAdapter(List<GameModel> gameList, Context context){
        this.gameList = gameList;
        this.context = context;

    }

    public void setGameList(List<GameModel> gameList) {
        this.gameList = gameList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamModelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_model, null));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamModelViewHolder holder, int position) {

        holder.homeScore.setText(String.valueOf(gameList.get(position).getHome_score()));
        holder.homeCity.setText(gameList.get(position).getHome_team().getName());
        holder.awayScore.setText(String.valueOf(gameList.get(position).getVisitor_score()));
        holder.awayCity.setText(gameList.get(position).getVisitor_team().getName());
        holder.logoAway.setImageResource(images[gameList.get(position).getVisitor_team().getTeam_id()-1]);
        holder.logoHome.setImageResource(images[gameList.get(position).getHome_team().getTeam_id()-1]);

    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
