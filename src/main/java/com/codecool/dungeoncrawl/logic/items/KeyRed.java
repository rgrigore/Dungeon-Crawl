package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class KeyRed extends Key {

    public KeyRed(Cell cell) {
        super(cell);
        setType(ItemType.KEY_RED);
    }
}
