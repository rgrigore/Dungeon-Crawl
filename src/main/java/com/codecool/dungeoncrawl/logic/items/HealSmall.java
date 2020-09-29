package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class HealSmall extends Item {
    private final String name = "Heal Small";
    private final int healthBoost = 2;

    public HealSmall(Cell cell) {
        super(cell);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void execute(Player player) {
        player.heal(healthBoost);
    }

    @Override
    public String getTileName() {
        return "heal_small";
    }
}
