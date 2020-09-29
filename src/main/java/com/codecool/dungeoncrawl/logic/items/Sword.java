package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class Sword extends Item {
    private final String name = "sword";

    public String getName() {
        return this.name;
    }

    @Override
    public void execute(Player player) {
        player.addItem(new Sword());
    }
}
