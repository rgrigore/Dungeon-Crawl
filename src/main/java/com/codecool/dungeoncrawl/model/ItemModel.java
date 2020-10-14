package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Item;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ItemModel extends BaseModel implements SQLData {
    private int mapId;
    private ItemModel next = null;

    private char symbol;
    private int x;
    private int y;

    public ItemModel(char symbol, int x, int y) {
        this.symbol = symbol;
        this.x = x;
        this.y = y;
    }

    public ItemModel(Item item) {
        this.symbol = item.getSymbol();
        this.x = item.getCell().getX();
        this.y = item.getCell().getY();
    }

    //region Setters/Getters
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
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

    public ItemModel getNext() {
        return next;
    }

    public void setNext(ItemModel next) {
        this.next = next;
    }

    //endregion

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        super.readSQL(stream, typeName);
        symbol = stream.readString().charAt(0);
        x = stream.readInt();
        y = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(String.valueOf(symbol));
        stream.writeInt(x);
        stream.writeInt(y);
    }
}
