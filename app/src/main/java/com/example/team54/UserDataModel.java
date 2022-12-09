package com.example.team54;

import java.util.ArrayList;
import java.util.List;

public class UserDataModel {

    String username;
    int balance;
    String word;
    List<Integer> vowel_positions;
    List<Integer> consonant_positions;

    public UserDataModel() {

    }

    public UserDataModel(String username) {
        this.username = username;
        this.balance = 0;
        this.word = "_";
        vowel_positions = new ArrayList<>();
        consonant_positions = new ArrayList<>();
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getVowel_positions() {
        return vowel_positions;
    }

    public void setVowel_positions(List<Integer> vowel_positions) {
        this.vowel_positions = vowel_positions;
    }

    public List<Integer> getConsonant_positions() {
        return consonant_positions;
    }

    public void setConsonant_positions(List<Integer> consonant_positions) {
        this.consonant_positions = consonant_positions;
    }
}
