package com.example.team54;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeamModelViewHolder extends RecyclerView.ViewHolder {

    public TextView homeScore, awayScore, homeCity, awayCity;
    public ImageView logoHome, logoAway;

    public TeamModelViewHolder(@NonNull View itemView) {
        super(itemView);
        this.homeScore = itemView.findViewById(R.id.homeTeamScore);
        this.awayScore = itemView.findViewById(R.id.awayTeamScore);
        this.homeCity = itemView.findViewById(R.id.home_city);
        this.awayCity = itemView.findViewById(R.id.away_city);
        this.logoHome = itemView.findViewById(R.id.logoHome);
        this.logoAway = itemView.findViewById(R.id.logoAway);
    }
}
