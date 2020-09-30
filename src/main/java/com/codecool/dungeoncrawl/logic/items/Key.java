package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public abstract class Key extends Item {

    public Key(Cell cell) {
        super(cell);
    }

    @Override
    public void execute(Player player) {
        super.execute(player);
        player.addItem(this);
    }
}
