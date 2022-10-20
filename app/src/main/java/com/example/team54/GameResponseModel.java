package com.example.team54;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameResponseModel {
    @SerializedName("data")
    List<GameModel> gameList;

    @SerializedName("meta")
    GameMetadataModel metadata;

    public GameResponseModel(List<GameModel> gameList, GameMetadataModel metadata) {
        this.gameList = gameList;
        this.metadata = metadata;
    }

    public List<GameModel> getGameList() {
        return gameList;
    }

    public GameMetadataModel getMetadata() {
        return metadata;
    }
}
