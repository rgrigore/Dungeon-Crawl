package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;

import java.sql.*;

public class GameStateModel extends BaseModel implements SQLData {
    private String name;
    private Date savedAt;
    private PlayerModel player;
    private MapModel map;

    public GameStateModel(String name, PlayerModel player, MapModel map) {
        this.name = name;
        this.player = player;
        this.map = map;
    }

    public GameStateModel(String name, GameMap gameMap) {
        this.name = name;
        this.player = new PlayerModel(gameMap.getPlayer());
        this.map = new MapModel(gameMap);
    }

    //region Setters/Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public MapModel getMap() {
        return map;
    }

    public void setMap(MapModel map) {
        this.map = map;
    }
    // endregion

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        super.readSQL(stream, typeName);
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(name);
        stream.writeInt(player.getId());
        stream.writeInt(map.getId());
    }
}
