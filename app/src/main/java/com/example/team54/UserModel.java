package com.example.team54;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

    private String email;
    private String UID;
    private String name;

    public UserModel() {

    }

    public UserModel(String email, String UID, String name) {
        this.UID = UID;
        this.email = email;
        this.name = name;
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

    @Override
    public String toString() {
        return "User = " + UID + ", " + email + ", " + name;
    }
}
