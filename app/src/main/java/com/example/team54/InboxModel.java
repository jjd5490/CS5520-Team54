package com.example.team54;

public class InboxModel {
    private String senderID;
    private String resourceID;
    private String date;


    public InboxModel() {

    }

    public InboxModel(String senderID, String resourceID, String date) {
        this.senderID = senderID;
        this.resourceID = resourceID;
        this.date = date;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
