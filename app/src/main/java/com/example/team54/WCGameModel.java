package com.example.team54;

public class WCGameModel {

    public String guest;
    public String host;
    public Boolean guest_ready;
    public Boolean host_ready;
    public UserDataModel guest_data;
    public UserDataModel host_data;
    public int round;

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

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean getGuest_ready() {
        return guest_ready;
    }

    public void setGuest_ready(Boolean guest_ready) {
        this.guest_ready = guest_ready;
    }

    public Boolean getHost_ready() {
        return host_ready;
    }

    public void setHost_ready(Boolean host_ready) {
        this.host_ready = host_ready;
    }

    public UserDataModel getGuest_data() {
        return guest_data;
    }

    public void setGuest_data(UserDataModel guest_data) {
        this.guest_data = guest_data;
    }

    public UserDataModel getHost_data() {
        return host_data;
    }

    public void setHost_data(UserDataModel host_data) {
        this.host_data = host_data;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
