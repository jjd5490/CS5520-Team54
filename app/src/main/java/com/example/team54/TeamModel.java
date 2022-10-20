package com.example.team54;

import com.google.gson.annotations.SerializedName;


public class TeamModel {
    @SerializedName("id")
    private Integer team_id;
    @SerializedName("abbreviation")
    private String city;
    @SerializedName("conference")
    private String conference;
    @SerializedName("division")
    private String division;
    @SerializedName("name")
    private String name;

    public TeamModel(Integer team_id, String city, String conference, String division, String name) {
        this.team_id = team_id;
        this.city = city;
        this.conference = conference;
        this.division = division;
        this.name = name;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public String getCity() {
        return city;
    }

    public String getConference() {
        return conference;
    }

    public String getDivision() {
        return division;
    }

    public String getName() {
        return name;
    }
}
