package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

@JsonIgnoreProperties(value = { "id", "playerId", "mapId" })
public class GameStateModel extends BaseModel implements SQLData {
    private String name;
    private Date savedAt;
    private int playerId;
    private PlayerModel player;
    private int mapId;
    private MapModel map;

    public GameStateModel() {
    }

    public GameStateModel(int id, String name, Date savedAt, int playerId, int mapId) {
        this.id = id;
        this.name = name;
        this.savedAt = savedAt;
        this.playerId = playerId;
        this.mapId = mapId;
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

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
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
