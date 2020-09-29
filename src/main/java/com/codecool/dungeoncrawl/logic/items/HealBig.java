package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class HealBig extends Item {
    private final String name = "Heal Big";
    private final int healthBoost = 5;

    public String getName() {
        return this.name;
    }

    @Override
    public void execute(Player player) {
        player.heal(healthBoost);
    }
}
