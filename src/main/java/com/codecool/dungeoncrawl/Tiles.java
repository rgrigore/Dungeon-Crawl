package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static final Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("water", new Tile(8, 5));
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("skull", new Tile(18, 24));
        tileMap.put("heart", new Tile(24, 22));
        tileMap.put("king", new Tile(28, 3));
        tileMap.put("crown", new Tile(12, 24));
        tileMap.put("soldier", new Tile(28, 0));

        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("tree", new Tile(1, 1));
        tileMap.put("rock", new Tile(5, 2));

        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("grass", new Tile(6, 0));
        tileMap.put("mud", new Tile(8, 0));

        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("ghost", new Tile(24, 0));

        tileMap.put("heal_big", new Tile(16, 28));
        tileMap.put("heal_small", new Tile(15, 29));

        tileMap.put("sword", new Tile(0, 30));
        tileMap.put("armor", new Tile(3, 23));

        tileMap.put("key_yellow", new Tile(16, 23));
        tileMap.put("key_blue", new Tile(17, 23));
        tileMap.put("key_red", new Tile(18, 23));

        tileMap.put("door_yellow", new Tile(5, 10));
        tileMap.put("door_blue", new Tile(0, 9));
        tileMap.put("door_red", new Tile(4, 9));

        tileMap.put("portal", new Tile(21, 23));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
