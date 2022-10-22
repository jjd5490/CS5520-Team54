package com.example.team54;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamModelAdapter extends RecyclerView.Adapter<TeamModelViewHolder>{

    private List<GameModel> gameList;
    private final Context context;


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
//        holder.logoHome.setImageResource(); setImageDrawable(getResources().getDrawable);
        holder.logoAway.setImageResource(R.drawable.team19);
        holder.logoHome.setImageResource(R.drawable.team25);


    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
