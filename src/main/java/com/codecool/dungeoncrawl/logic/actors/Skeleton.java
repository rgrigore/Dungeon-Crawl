package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import javafx.animation.AnimationTimer;

import java.util.concurrent.ThreadLocalRandom;

public class Skeleton extends Actor {
    private static final int STARTING_HEALTH = 10;
    private static final int STARTING_DAMAGE = 2;
    Movement movement = this.new Movement();

    public Skeleton(Cell cell) {
        super(cell);
        setCellType(CellType.MOB);

        setHealth(STARTING_HEALTH);
        setDamage(STARTING_DAMAGE);

        movement.start();
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public char getSymbol() {
        return 's';
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        switch (nextCell.getType()) {
            case FLOOR: super.move(dx, dy); break;
            case PLAYER: attack(nextCell.getActor()); break;
        }
    }

    @Override
    protected void die() {
        movement.stop();
        super.die();
    }

    private class Movement extends AnimationTimer {
        private static final int MIN_MOVE_WAIT = 30;
        private static final int MAX_MOVE_WAIT = 121;
        private int wait = MAX_MOVE_WAIT;

        @Override
        public void handle(long l) {
            if (wait == 0) {
                int x = ThreadLocalRandom.current().nextInt(-1, 2);
                int y = ThreadLocalRandom.current().nextInt(-1, 2);
                Skeleton.this.move(x, y);

                wait = ThreadLocalRandom.current().nextInt(MIN_MOVE_WAIT, MAX_MOVE_WAIT);
            } else {
                wait--;
            }
        }
    }
}
