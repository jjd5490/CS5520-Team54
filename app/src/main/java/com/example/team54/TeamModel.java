package com.example.team54;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class TeamModel implements Parcelable {
    @SerializedName("id")
    private final Integer team_id;
    @SerializedName("abbreviation")
    private final String city;
    @SerializedName("conference")
    private final String conference;
    @SerializedName("division")
    private final String division;
    @SerializedName("name")
    private final String name;

    public TeamModel(Integer team_id, String city, String conference, String division, String name) {
        this.team_id = team_id;
        this.city = city;
        this.conference = conference;
        this.division = division;
        this.name = name;
    }

    protected TeamModel(Parcel in) {
        if (in.readByte() == 0) {
            team_id = null;
        } else {
            team_id = in.readInt();
        }
        city = in.readString();
        conference = in.readString();
        division = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (team_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(team_id);
        }
        dest.writeString(city);
        dest.writeString(conference);
        dest.writeString(division);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TeamModel> CREATOR = new Creator<TeamModel>() {
        @Override
        public TeamModel createFromParcel(Parcel in) {
            return new TeamModel(in);
        }

        @Override
        public TeamModel[] newArray(int size) {
            return new TeamModel[size];
        }
    };

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
