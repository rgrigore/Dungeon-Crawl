package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Actor;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class MobModel extends BaseModel implements SQLData {
    private int mapId;

    private char symbol;
    private int hp;
    private int x;
    private int y;

    public MobModel(char symbol, int hp, int x, int y) {
        this.symbol = symbol;
        this.hp = hp;
        this.x = x;
        this.y = y;
    }

    public MobModel(Actor mob) {
        this.symbol = mob.getSymbol();
        this.hp = mob.getCurrentHealth();
        this.x = mob.getX();
        this.y = mob.getY();
    }

    //region Setters/Getters
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    //endregion

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        super.readSQL(stream, typeName);
        symbol = stream.readString().charAt(0);
        hp = stream.readInt();
        x = stream.readInt();
        y = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(String.valueOf(symbol));
        stream.writeInt(hp);
        stream.writeInt(x);
        stream.writeInt(y);
    }
}
