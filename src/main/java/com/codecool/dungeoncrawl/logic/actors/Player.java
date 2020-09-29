package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        switch (nextCell.getType()) {
            case FLOOR: super.move(dx, dy); break;
            case MOB: attack(nextCell.getActor()); break;
        }
    }

    @Override
    protected void die() {
        // TODO
    }
}
