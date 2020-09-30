package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private CellType cellType;
    private CellType oldCellType;
    private int health = 10;
    private int currentHealth;
    private int damage = 2;
    private boolean dead = false;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        currentHealth = health;
    }

    protected void setCellType(CellType cellType) {
        this.cellType = cellType;
        oldCellType = CellType.FLOOR;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        oldCellType = CellType.FLOOR;
        currentHealth = health;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        cell.setType(oldCellType);
        nextCell.setActor(this);
        oldCellType = nextCell.getType();
        nextCell.setType(cellType);
        cell = nextCell;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
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

    public void increaseHealth(int increase) {
        health += increase;
        heal(increase);
    }

    public void increaseDamage(int increase) {
        damage += increase;
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
        cell.setType(oldCellType);
    }
}
