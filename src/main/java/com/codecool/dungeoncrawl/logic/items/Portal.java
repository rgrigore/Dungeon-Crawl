package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Portal extends Item {

    public Portal(Cell cell) {
        super(cell);
    }

    @Override
    public void execute(Player player) {
        MapLoader.loadNextMap(player);
    }

    @Override
    public String getTileName() {
        return "portal";
    }

    @Override
    public char getSymbol() {
        return '^';
    }
}
