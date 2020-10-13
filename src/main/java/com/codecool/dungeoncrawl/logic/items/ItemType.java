package com.codecool.dungeoncrawl.logic.items;

public enum ItemType {
    KEY_BLUE("Blue key", "key_blue", 'b'),
    KEY_RED("Red key", "key_red", 'r'),
    KEY_YELLOW("Yellow key", "key_yellow", 'y'),
    DOOR_BLUE("Blue door", "door_blue", 'B'),
    DOOR_RED("Red door", "door_red", 'R'),
    DOOR_YELLOW("Yellow door", "door_yellow", 'Y'),
    SWORD("Sword", "sword", '|'),
    ARMOR("Armor", "armor", '-'),
    HEAL_BIG("Steak", "heal_big", '+'),
    HEAL_SMALL("Apple", "heal_small", '*'),
    PORTAL("Portal", "portal", '^');



    private final String name;
    private final String tileName;
    private final char symbol;

    ItemType(String name, String tileName, char symbol) {
        this.name = name;
        this.tileName = tileName;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }
    public String getTileName() {
        return tileName;
    }
    public char getSymbol() {
        return symbol;
    }
}
