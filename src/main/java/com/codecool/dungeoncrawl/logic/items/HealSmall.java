package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class HealSmall extends Item {
    private static final int HEALTH_BOOST = 2;

    public HealSmall(Cell cell) {
        super(cell);
        setType(ItemType.HEAL_SMALL);
    }

    @Override
    public void execute(Player player) {
        super.execute(player);
        player.heal(HEALTH_BOOST);
    }
}
