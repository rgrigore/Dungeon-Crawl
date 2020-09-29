package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int currentHealth;
    private int damage = 2;
    private boolean dead = false;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        currentHealth = health;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public int getHealth() {
        return health;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void heal(int lifeBoost) {
        this.currentHealth += lifeBoost;
    }

    private void takeDamage(int damage) {
        currentHealth -= damage;
        if (dead = currentHealth <= 0) {
            die();
        }
    }

    protected void attack(Actor target) {
        target.defend(this, damage);
    }

    protected void defend(Actor target, int damage) {
        takeDamage(damage);
        if (!dead) {
            target.takeDamage(this.damage);
        }
    }

    protected void die() {
        cell.setActor(null);
        cell.setType(CellType.FLOOR);
    }
}
