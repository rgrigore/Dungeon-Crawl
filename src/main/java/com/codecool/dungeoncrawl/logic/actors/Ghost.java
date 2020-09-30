package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import javafx.animation.AnimationTimer;

import java.util.concurrent.ThreadLocalRandom;

public class Ghost extends Actor {
    Movement movement = this.new Movement();

    public Ghost(Cell cell) {
        super(cell);
        setCellType(CellType.MOB);

        movement.start();
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        if(nextCell!=null) {
            switch (nextCell.getType()) {
                case PLAYER: attack(nextCell.getActor()); break;
                case MOB: break;
                default: super.move(dx, dy);
            }
        }
    }

    @Override
    protected void die() {
        movement.stop();
        super.die();
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    private class Movement extends AnimationTimer {
        private static final int MIN_MOVE_WAIT = 20;
        private static final int MAX_MOVE_WAIT = 60;
        private int wait = MAX_MOVE_WAIT;

        @Override
        public void handle(long l) {
            if (wait == 0) {
                int x = ThreadLocalRandom.current().nextInt(-1, 2);
                int y = ThreadLocalRandom.current().nextInt(-1, 2);
                Ghost.this.move(x, y);

                wait = ThreadLocalRandom.current().nextInt(MIN_MOVE_WAIT, MAX_MOVE_WAIT);
            } else {
                wait--;
            }
        }
    }
}
