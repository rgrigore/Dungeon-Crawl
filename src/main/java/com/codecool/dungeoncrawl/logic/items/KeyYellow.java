package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class KeyYellow extends Key {
    public KeyYellow(Cell cell) {
        super(cell);
        setType(ItemType.KEY_YELLOW);
    }

    @Override
    public String getTileName() {
        return "key_yellow";
    }
}
