package com.example.team54;

public class WCGameModel {

    String guest;
    String host;
    Boolean guest_ready;
    Boolean host_ready;
    UserDataModel guest_data;
    UserDataModel host_data;
    int round;

    public WCGameModel() {

    }

    public WCGameModel(String guest, String host, Boolean guest_ready, Boolean host_ready, int round,
                       UserDataModel guest_data, UserDataModel host_data) {
        this.host = host;
        this.guest = guest;
        this.guest_ready = false;
        this.host_ready = false;
        this.round = round;
        this.guest_data = guest_data;
        this.host_data = host_data;
    }
}
