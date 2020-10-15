package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

@JsonIgnoreProperties(value = { "id", "playerId" })
public class InventoryModel extends BaseModel implements SQLData {

    private int playerId;
    private Character[] items;

    public InventoryModel() {
    }

    public InventoryModel(int playerId, Character[] items) {
        this.playerId = playerId;
        this.items = items;
    }

    public InventoryModel(HashMap<ItemType, Integer> inventory) {
        ArrayList<Character> items = new ArrayList<>();

        inventory.forEach((k, v) -> {
            while (v-- > 0) {
                items.add(k.getSymbol());
            }
        });

        this.items = items.toArray(Character[]::new);
    }

    //region Setters/Getters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Character[] getItems() {
        return items;
    }

    public void setItems(Character[] items) {
        this.items = items;
    }
    //endregion

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        super.readSQL(stream, typeName);
        items = (Character[]) stream.readArray().getArray();
    }

    @Override
    public void writeSQL(SQLOutput stream) {
    }
}
