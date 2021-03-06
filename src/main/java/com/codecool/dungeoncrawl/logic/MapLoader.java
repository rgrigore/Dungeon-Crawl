package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.MapModel;
import com.codecool.dungeoncrawl.model.MobModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class MapLoader {
    private static int level = 1;

    private static final String[] MAPS = new String[] {
            "deadMap",
            "map",
            "map2",
            "winMap"
    };

    public static GameMap loadMap(int level, Player player) {
        InputStream is = MapLoader.class.getResourceAsStream(String.format("/%s.txt", MAPS[MapLoader.level = level]));
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine(); // empty line

        GameMap map = new GameMap(level, width, height, CellType.NULL);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    if (line.charAt(x) == '@') {
                        cell.setType(CellType.PLAYER);
                        if (player == null) {
                            map.setPlayer(new Player(cell));
                        } else {
                            player.setCell(cell);
                            map.setPlayer(player);
                        }
                    } else {
                        setCellType(cell, line.charAt(x));
                    }
                }
            }
        }
        return map;
    }

    public static void loadMap(MapModel mapModel, PlayerModel playerModel) {
        MapLoader.level = mapModel.getLevel();
        GameMap map = new GameMap(level, mapModel.getWidth(), mapModel.getHeight(), CellType.NULL);

        AtomicInteger y = new AtomicInteger();
        AtomicInteger x = new AtomicInteger();
        mapModel.getTerrain().chars().forEach(c -> {
            setCellType(map.getCell(x.getAndIncrement(), y.get()), (char) c);
            if (x.get() == map.getWidth()) {
                y.getAndIncrement();
                x.set(0);
            }
        });

        ItemModel itemModel = mapModel.getItemModels();
        while (itemModel != null) {
            Cell cell = map.getCell(itemModel.getX(), itemModel.getY());
            setCellType(cell, itemModel.getSymbol());
            itemModel = itemModel.getNext();
        }

        MobModel mobModel = mapModel.getMobModels();
        while (mobModel != null) {
            Cell cell = map.getCell(mobModel.getX(), mobModel.getY());
            setCellType(cell, mobModel.getSymbol());
            mobModel = mobModel.getNext();
        }

        Cell cell = map.getCell(playerModel.getX(), playerModel.getY());
        cell.setType(CellType.PLAYER);
        map.setPlayer(new Player(cell, playerModel));

        Main.setMap(map);
    }

    private static void setCellType(Cell cell, char symbol) {
        switch (symbol) {
            case '/':
                cell.setTile("skull", symbol);
            case 'x':
                cell.setTile("heart", symbol);
            case 'w':
                cell.setTile("water", symbol);
            case 'k':
                cell.setTile("king", symbol);
            case 'c':
                cell.setTile("crown", symbol);
            case 'n':
                cell.setTile("soldier", symbol);
            case ' ':
                cell.setType(CellType.EMPTY);
                break;
            case 't':
                cell.setTile("tree", symbol);
            case 'o':
                cell.setTile("rock", symbol);
            case '#':
                cell.setType(CellType.WALL);
                break;
            case 'p':
                cell.setTile("grass", symbol);
            case 'm':
                cell.setTile("mud", symbol);
            case '.':
                cell.setType(CellType.FLOOR);
                break;
            case '+':
                cell.setType(CellType.ITEM);
                new HealBig(cell);
                break;
            case '*':
                cell.setType(CellType.ITEM);
                new HealSmall(cell);
                break;
            case 'b':
                cell.setType(CellType.ITEM);
                new KeyBlue(cell);
                break;
            case 'r':
                cell.setType(CellType.ITEM);
                new KeyRed(cell);
                break;
            case 'y':
                cell.setType(CellType.ITEM);
                new KeyYellow(cell);
                break;
            case 'B':
                cell.setType(CellType.DOOR);
                new Door(cell, Door.DoorColor.BLUE);
                break;
            case 'R':
                cell.setType(CellType.DOOR);
                new Door(cell, Door.DoorColor.RED);
                break;
            case 'Y':
                cell.setType(CellType.DOOR);
                new Door(cell, Door.DoorColor.YELLOW);
                break;
            case '|':
                cell.setType(CellType.ITEM);
                new Sword(cell);
                break;
            case '-':
                cell.setType(CellType.ITEM);
                new Armor(cell);
                break;
            case 's':
                new Skeleton(cell);
                cell.setType(CellType.MOB);
                break;
            case 'g':
                new Ghost(cell);
                cell.setType(CellType.MOB);
                break;
            case '^':
                new Portal(cell);
                cell.setType(CellType.PORTAL);
                break;
            default:
                throw new RuntimeException("Unrecognized character: '" + symbol + "'");
        }
    }

    public static void loadNextMap(Player player) {
        Main.setMap(loadMap(++level, player));
    }

    public static void loadGameOverMap(Player player) {
        Main.setMap(loadMap(0, player));
    }

    public static void restartGame() {
        Main.setMap(loadMap(level = 1, null));
    }

    public static int getLevel() {
        return level;
    }
}
