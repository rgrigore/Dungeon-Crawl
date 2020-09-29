package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.*;

public class Player extends Actor {
    HashMap<Item, Integer> Inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public void addItem(Item item) {
        if(Inventory.containsKey(item)) {
            int newCount = Inventory.get(item)+1;
            Inventory.put(item, newCount);
        } else {
            Inventory.put(item, 1);
        }
    }

    public List<String> getInvetory() {
        ArrayList<String> inventory = new ArrayList<>();
        Iterator it = Inventory.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            inventory.add(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return inventory;
    }
}
