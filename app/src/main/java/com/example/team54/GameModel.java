package com.example.team54;

import com.google.gson.annotations.SerializedName;

public class GameModel {
    @SerializedName("home_team_score")
    private Integer home_score;

    @SerializedName("visitor_team_score")
    private Integer visitor_score;
    private String home_team;
    private String visitor_team;
    private boolean postseason;
    private Integer season;
    private String status;

    public GameModel(Integer home_score, Integer visitor_score, String home_team, String visitor_team,
                     boolean postseason, Integer season, String status) {
        this.home_score = home_score;
        this.visitor_score = visitor_score;
        this.home_team = home_team;
        this.visitor_team = visitor_team;
        this.postseason = postseason;
        this.season = season;
        this.status = status;
    }

    public Integer getHome_score() {
        return home_score;
    }

    public Integer getVisitor_score() {
        return visitor_score;
    }

    public String getHome_team() {
        return home_team;
    }

    public String getVisitor_team() {
        return visitor_team;
    }

    public boolean isPostseason() {
        return postseason;
    }

    public Integer getSeason() {
        return season;
    }

    public String getStatus() {
        return status;
    }
}
