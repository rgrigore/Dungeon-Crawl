package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class HealBig extends Item {
    private static final int HEALTH_BOOST = 5;

    public HealBig(Cell cell) {
        super(cell);
        setType(ItemType.HEAL_BIG);
    }

    @Override
    public void execute(Player player) {
        super.execute(player);
        player.heal(HEALTH_BOOST);
    }
}
