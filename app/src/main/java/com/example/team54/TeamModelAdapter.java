package com.example.team54;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamModelAdapter extends RecyclerView.Adapter<TeamModelViewHolder>{

    private final List<GameModel> gameList;
    private final Context context;

    public TeamModelAdapter(List<GameModel> gameList, Context context){
        this.gameList = gameList;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamModelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_model, null));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamModelViewHolder holder, int position) {
        holder.name.setText(gameList.get(position).getHome_team().getName());
        holder.city.setText(gameList.get(position).getHome_team().getCity());
        holder.id.setText(String.valueOf(gameList.get(position).getHome_team().getTeam_id()));
        holder.conference.setText(gameList.get(position).getHome_team().getConference());
        holder.division.setText(gameList.get(position).getHome_team().getDivision());

//        holder.score.setText(gameList.get(position).getHome_team().getCity());


//        holder.id.setText(String.valueOf(gameList.get(position).getSeason()));
//        holder.name.setText(String.valueOf(gameList.get(position).getId()));
//        holder.score.setText(gameList.get(position).getHome_team().getName());
        //holder.score.setText("TEAM");
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
