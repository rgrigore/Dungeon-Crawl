package com.codecool.dungeoncrawl.logic.items;

public enum ItemType {
    KEY_BLUE("Blue key"),
    KEY_RED("Red key"),
    KEY_YELLOW("Yellow key"),
    SWORD("Sword"),
    ARMOR("Armor");


    private final String name;

    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
