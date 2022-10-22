package com.example.team54;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeamModelViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView id;
    public TextView city;
    public TextView division;
    public TextView conference;

    public TeamModelViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.team_name);
        this.id = itemView.findViewById(R.id.team_id);
        this.city = itemView.findViewById(R.id.team_city);
        this.division = itemView.findViewById(R.id.team_division);
        this.conference = itemView.findViewById(R.id.team_conference);
    }
}
