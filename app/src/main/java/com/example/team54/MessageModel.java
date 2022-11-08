package com.example.team54;

public class MessageModel implements Comparable<MessageModel> {
    private String senderID;
    private String recipientID;
    private String resourceID;
    private String dateTime;

    public MessageModel() {

    }

    public MessageModel(String senderID, String recipientID, String resourceID, String dateTime) {
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.resourceID = resourceID;
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int compareTo(MessageModel b) {
        if (b == null) {
            return -1;
        }
        long aTime = Long.parseLong(this.getDateTime());
        long bTime = Long.parseLong(b.getDateTime());

        return Long.compare(aTime, bTime);

/**
        if (aTime == bTime) {
            return 0;
        } else if (aTime > bTime){
            return -1;
        } else {
            return 1;
        }
 **/
    }
}
