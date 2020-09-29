package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class Key extends Item {
    private final String name = "key";

    public String getName() {
        return this.name;
    }

    @Override
    public void execute(Player player) {
        player.addItem(new Key());
    }
}
