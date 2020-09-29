package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.*;

public class Player extends Actor {
    HashMap<Item, Integer> inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell);
        setCellType(CellType.PLAYER);
    }

    @Override
    public String getTileName() {
        return "player";
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        switch (nextCell.getType()) {
            case ITEM: nextCell.getItem().execute(this);
            case FLOOR: super.move(dx, dy); break;
            case MOB: attack(nextCell.getActor()); break;
        }
    }

    @Override
    protected void die() {
        // TODO
    }

    public void addItem(Item item) {
        if(inventory.containsKey(item)) {
            int newCount = inventory.get(item)+1;
            inventory.put(item, newCount);
        } else {
            inventory.put(item, 1);
        }
    }

    public List<String> getInventory() {
        ArrayList<String> inventory = new ArrayList<>();
        this.inventory.forEach((key, value) -> {
            inventory.add(String.format("%s x%d", key.getClass().getSimpleName(), value));
        });
        return inventory;
    }
}
