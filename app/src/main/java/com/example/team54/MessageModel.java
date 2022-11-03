package com.example.team54;

public class MessageModel {
    private String senderID;
    private String recipientID;
    private String resourceID;

    public MessageModel() {

    }

    public MessageModel(String senderID, String recipientID, String resourceID) {
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.resourceID = resourceID;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }
}
