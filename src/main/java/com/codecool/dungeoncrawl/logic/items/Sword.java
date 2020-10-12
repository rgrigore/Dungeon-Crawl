package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Sword extends Item {
    private static final int DAMAGE_INCREASE = 2;

    public Sword(Cell cell) {
        super(cell);
        setType(ItemType.SWORD);
    }

    @Override
    public void execute(Player player) {
        super.execute(player);
        player.addItem(this);
        player.increaseDamage(DAMAGE_INCREASE);
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
