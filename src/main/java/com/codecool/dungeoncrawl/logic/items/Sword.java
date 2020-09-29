package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Sword extends Item {
    private final String name = "sword";

    public Sword(Cell cell) {
        super(cell);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void execute(Player player) {
        super.execute(player);
        player.addItem(this);
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
