package com.codecool.dungeoncrawl.logic;

public enum CellType {
    NULL("", ' '),
    EMPTY("empty", ' '),
    FLOOR("floor", '.'),
    WALL("wall", '#'),
    PLAYER("player", '.'),
    MOB("mob", '.'),
    ITEM("item", '.'),
    DOOR("door", '.'),
    PORTAL("portal", '.');

    private final String tileName;
    private final char symbol;

    CellType(String tileName, char symbol) {
        this.tileName = tileName;
        this.symbol = symbol;
    }

    public String getTileName() {
        return tileName;
    }
    public char getSymbol() {
        return symbol;
    }
}
