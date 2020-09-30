package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Armor extends Item {
    private static final int HEALTH_INCREASE = 5;

    public Armor(Cell cell) {
        super(cell);
        setType(ItemType.ARMOR);
    }

    @Override
    public void execute(Player player) {
        super.execute(player);
        player.addItem(this);
        player.increaseHealth(HEALTH_INCREASE);
    }

    @Override
    public String getTileName() {
        return "armor";
    }
}
