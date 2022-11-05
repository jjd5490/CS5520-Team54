package com.example.team54;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

    private String email;
    private String UID;
    private String name;
    private String deviceToken;
    private List<String> contacts;
    private List<MessageModel> messagesReceived;
    private List<MessageModel> messagesSent;
    private InboxModel inbox;

    public UserModel() {

    }

    public UserModel(String email, String UID, String name, String deviceToken, List<String> contacts,
                     List<MessageModel> messagesReceived, List<MessageModel> messagesSent,
                     InboxModel inbox) {
        this.email = email;
        this.UID = UID;
        this.name = name;
        this.deviceToken = deviceToken;
        this.contacts = contacts;
        this.messagesReceived = messagesReceived;
        this.messagesSent = messagesSent;
        this.inbox = inbox;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<MessageModel> getMessagesReceived() {
        return messagesReceived;
    }

    public void setMessagesReceived(List<MessageModel> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public List<MessageModel> getMessagesSent() {
        return messagesSent;
    }

    public void setMessagesSent(List<MessageModel> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public InboxModel getInbox() {
        return inbox;
    }

    public void setInbox(InboxModel inbox) {
        this.inbox = inbox;
    }

    public void receiveMessage(MessageModel m) {
        if (this.messagesReceived == null) {
            messagesReceived = new ArrayList<>();
        }
        this.messagesReceived.add(m);
    }

    @Override
    public String toString() {
        return "User = " + UID + ", " + email + ", " + name;
    }
}
