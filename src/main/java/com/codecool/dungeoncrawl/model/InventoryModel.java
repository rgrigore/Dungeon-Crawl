package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.ItemType;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

public class InventoryModel extends BaseModel implements SQLData {

    private int player_id;
    private Character[] items;

    public InventoryModel(int player_id, Character[] items) {
        this.player_id = player_id;
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
    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
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
