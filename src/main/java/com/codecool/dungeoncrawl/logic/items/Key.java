package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Key extends Item {
    private final String name = "key";

    public Key(Cell cell) {
        super(cell);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void execute(Player player) {
        player.addItem(this);
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
