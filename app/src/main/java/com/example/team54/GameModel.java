package com.example.team54;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GameModel implements Parcelable {
    @SerializedName("id")
    private final Integer id;
    @SerializedName("date")
    private final String date;
    @SerializedName("home_team_score")
    private final Integer home_score;

    @SerializedName("visitor_team_score")
    private final Integer visitor_score;
    private final TeamModel home_team;
    private final TeamModel visitor_team;
    private final boolean postseason;
    private final Integer season;


    private final String status;

    public GameModel(Integer id, String date, Integer home_score, Integer visitor_score, TeamModel home_team,
                     TeamModel visitor_team, boolean postseason, Integer season, String status) {
        this.id = id;
        this.date = date;
        this.home_score = home_score;
        this.visitor_score = visitor_score;
        this.home_team = home_team;
        this.visitor_team = visitor_team;
        this.postseason = postseason;
        this.season = season;
        this.status = status;
    }

    protected GameModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        date = in.readString();
        if (in.readByte() == 0) {
            home_score = null;
        } else {
            home_score = in.readInt();
        }
        if (in.readByte() == 0) {
            visitor_score = null;
        } else {
            visitor_score = in.readInt();
        }
        home_team = in.readParcelable(TeamModel.class.getClassLoader());
        visitor_team = in.readParcelable(TeamModel.class.getClassLoader());
        postseason = in.readByte() != 0;
        if (in.readByte() == 0) {
            season = null;
        } else {
            season = in.readInt();
        }
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(date);
        if (home_score == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(home_score);
        }
        if (visitor_score == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(visitor_score);
        }
        dest.writeParcelable(home_team, flags);
        dest.writeParcelable(visitor_team, flags);
        dest.writeByte((byte) (postseason ? 1 : 0));
        if (season == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(season);
        }
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameModel> CREATOR = new Creator<GameModel>() {
        @Override
        public GameModel createFromParcel(Parcel in) {
            return new GameModel(in);
        }

        @Override
        public GameModel[] newArray(int size) {
            return new GameModel[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Integer getHome_score() {
        return home_score;
    }

    public Integer getVisitor_score() {
        return visitor_score;
    }


    public TeamModel getHome_team() {
        return home_team;
    }


    public TeamModel getVisitor_team() {
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
