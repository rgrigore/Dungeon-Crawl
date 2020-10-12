package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Door extends Item {

    private final DoorColor color;
    private final ItemType key;

    public Door(Cell cell, DoorColor color) {
        super(cell);
        this.color = color;
        switch (color) {
            case RED: key = ItemType.KEY_RED; break;
            case YELLOW: key = ItemType.KEY_YELLOW; break;
            case BLUE: key = ItemType.KEY_BLUE; break;
            default: key = null;
        }
    }

    @Override
    public String getTileName() {
        return String.format("door_%s", color.getName());
    }

    @Override
    public void execute(Player player) {
        player.attemptUnlockDoor(this);
    }

    public ItemType getKey() {
        return key;
    }

    public void unlock() {
        super.execute(null);
    }

    public enum DoorColor {
        BLUE("blue"),
        RED("red"),
        YELLOW("yellow");

        private final String name;

        DoorColor(String color) {
            this.name = color;
        }

        public String getName() {
            return name;
        }
    }
}
