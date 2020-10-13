package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Door;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player extends Actor {
    private static final int STARTING_HEALTH = 20;
    private static final int STARTING_DAMAGE = 2;
    private String name;
    private final HashMap<ItemType, Integer> inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell);
        setCellType(CellType.PLAYER);
        setHealth(STARTING_HEALTH);
        setDamage(STARTING_DAMAGE);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getTileName() {
        return "player";
    }

    @Override
    public char getSymbol() {
        return '@';
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        if (nextCell != null) {
            switch (nextCell.getType()) {
                case EMPTY:
                case WALL:
                    if ((name.equals("Razvan") || name.equals("Marius"))) {
                        super.move(dx, dy);
                        break;
                    }
                    break;
                case ITEM: nextCell.getItem().execute(this);
                case FLOOR: super.move(dx, dy); break;
                case PORTAL: super.move(dx, dy);
                case DOOR: nextCell.getItem().execute(this); break;
                case MOB: attack(nextCell.getActor()); break;
            }
        }
    }

    @Override
    protected void die() {
        MapLoader.loadGameOverMap(this);
    }

    public void addItem(Item item) {
        ItemType itemType = item.getType();
        if(inventory.containsKey(itemType)) {
            int newCount = inventory.get(itemType)+1;
            inventory.put(itemType, newCount);
        } else {
            inventory.put(itemType, 1);
        }
    }

    public HashMap<ItemType, Integer> getInventory() {
        return inventory;
    }

    public List<String> getInventoryDisplay() {
        ArrayList<String> inventory = new ArrayList<>();
        this.inventory.forEach((key, value) -> {
            inventory.add(String.format("%s x%d", key.getName(), value));
        });
        return inventory;
    }

    public void attemptUnlockDoor(Door door) {
        ItemType doorKey = door.getKey();
        if (inventory.containsKey(doorKey)) {
            int count = inventory.get(doorKey) - 1;
            if (count == 0) {
                inventory.remove(doorKey);
            } else {
                inventory.put(doorKey, count);
            }
            door.unlock();
        }
    }
}
