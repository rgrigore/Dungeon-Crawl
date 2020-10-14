package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class PlayerModel extends BaseModel implements SQLData {
    private String name;
    private int max_hp;
    private int hp;
    private int attack;
    private int x;
    private int y;

    private InventoryModel inventory;

    public PlayerModel(String name, int max_hp, int hp, int attack, int x, int y) {
        this.name = name;
        this.max_hp = max_hp;
        this.hp = hp;
        this.attack = attack;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.name = player.getName();
        this.max_hp = player.getHealth();
        this.hp = player.getCurrentHealth();
        this.attack = player.getDamage();
        this.x = player.getX();
        this.y = player.getY();
        this.inventory = new InventoryModel(player.getInventory());
    }

    //region Getters/Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
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

    public InventoryModel getInventory() {
        return inventory;
    }

    public void setInventory(InventoryModel inventory) {
        this.inventory = inventory;
    }

    //endregion


    @Override
    public void setId(int id) {
        super.setId(id);
        inventory.setPlayer_id(id);
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        super.readSQL(stream, typeName);
        name = stream.readString();
        max_hp = stream.readInt();
        hp = stream.readInt();
        attack = stream.readInt();
        x = stream.readInt();
        y = stream.readInt();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(name);
        stream.writeInt(max_hp);
        stream.writeInt(hp);
        stream.writeInt(attack);
        stream.writeInt(x);
        stream.writeInt(y);
    }
}
