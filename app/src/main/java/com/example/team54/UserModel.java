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

    public InboxModel getInbox() {
        return inbox;
    }

    public void setInbox(InboxModel inbox) {
        this.inbox = inbox;
    }

    public UserModel() {

    }

    public UserModel(String email, String UID, String name, String deviceToken, List<String> contacts,
                     List<MessageModel> messagesReceived, List<MessageModel> messagesSent,
                     InboxModel inbox) {
        this.UID = UID;
        this.email = email;
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

    public String getUID() {
        return UID;
    }

    public String getName() {
        return name;
    }

    public List<MessageModel> getMessagesReceived() {
        return messagesReceived;
    }

    public List<MessageModel> getMessagesSent() {
        return messagesSent;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public void setMessagesReceived(List<MessageModel> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public void setMessagesSent(List<MessageModel> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public void receiveMessage(MessageModel m) {
        if (this.messagesReceived == null) {
            messagesReceived = new ArrayList<>();
        }
        this.messagesReceived.add(m);
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public String toString() {
        return "User = " + UID + ", " + email + ", " + name;
    }
}
