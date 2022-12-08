package com.example.team54;

import android.os.Parcel;
import android.os.Parcelable;

public class GameRecyclerObject implements Parcelable {
    private final Integer home_id;
    private final Integer visitor_id;
    private final String home_city;
    private final String visitor_city;
    private final String home_name;
    private final String visitor_name;
    private final Integer home_score;
    private final Integer visitor_score;
    private final String status;
    private final String date;

    public GameRecyclerObject(Integer home_id, Integer visitor_id, String home_city,
                              String visitor_city, String home_name, String visitor_name,
                              Integer home_score, Integer visitor_score, String status,
                              String date) {

        this.home_id = home_id;
        this.visitor_id = visitor_id;
        this.home_city = home_city;
        this.visitor_city = visitor_city;
        this.home_name = home_name;
        this.visitor_name = visitor_name;
        this.home_score = home_score;
        this.visitor_score = visitor_score;
        this.status = status;
        this.date = date;
    }

    protected GameRecyclerObject(Parcel in) {
        home_id = in.readInt();
        visitor_id = in.readInt();
        home_city = in.readString();
        visitor_city = in.readString();
        home_name = in.readString();
        visitor_name = in.readString();
        home_score = in.readInt();
        visitor_score = in.readInt();
        status = in.readString();
        date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<GameRecyclerObject> CREATOR = new Parcelable.Creator<GameRecyclerObject>() {
        public GameRecyclerObject createFromParcel(Parcel in) {
            return new GameRecyclerObject(in);
        }
        public GameRecyclerObject[] newArray(int size) {
            return new GameRecyclerObject[size];
        }
    };
}
