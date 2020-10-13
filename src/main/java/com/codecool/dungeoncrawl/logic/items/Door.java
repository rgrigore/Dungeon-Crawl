package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Door extends Item {

    private final ItemType key;

    public Door(Cell cell, DoorColor color) {
        super(cell);
        switch (color) {
            case BLUE: key = ItemType.KEY_BLUE; setType(ItemType.DOOR_BLUE); break;
            case RED: key = ItemType.KEY_RED; setType(ItemType.DOOR_RED); break;
            case YELLOW: key = ItemType.KEY_YELLOW; setType(ItemType.DOOR_YELLOW); break;
            default: key = null;
        }
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
        BLUE, RED, YELLOW
    }
}
