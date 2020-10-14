package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final int level;

    private Player player;

    public GameMap(int level, int width, int height, CellType defaultCellType) {
        this.level = level;
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cells[x][y];
        } else {
            return null;
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevel() {
        return level;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
