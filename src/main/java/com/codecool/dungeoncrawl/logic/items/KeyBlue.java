package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class KeyBlue extends Key {

    public KeyBlue(Cell cell) {
        super(cell);
        setType(ItemType.KEY_BLUE);
    }

    @Override
    public String getTileName() {
        return "key_blue";
    }
}
