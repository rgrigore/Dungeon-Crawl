package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    private static int level = 1;

    private static final String[] MAPS = new String[] {
            "deadMap",
            "map",
            "map2"
    };

    public static GameMap loadMap(int level, Player player) {
        InputStream is = MapLoader.class.getResourceAsStream(String.format("/%s.txt", MAPS[level]));
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case '/':
                            cell.setTileName("skull");
                        case 'x':
                            cell.setTileName("heart");
                        case 'w':
                            cell.setTileName("water");
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case 't':
                            cell.setTileName("tree");
                        case 'o':
                            cell.setTileName("rock");
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case 'p':
                            cell.setTileName("grass");
                        case 'm':
                            cell.setTileName("mud");
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case '+':
                            cell.setType(CellType.ITEM);
                            new HealBig(cell);
                            break;
                        case '±':
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
                            cell.setType(CellType.MOB);
                            new Skeleton(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.MOB);
                            new Ghost(cell);
                            break;
                        case '^':
                            cell.setType(CellType.PORTAL);
                            new Portal(cell);
                            break;
                        case '@':
                            cell.setType(CellType.PLAYER);
                            if (player == null) {
                                map.setPlayer(new Player(cell));
                            } else {
                                player.setCell(cell);
                                map.setPlayer(player);
                            }
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static void loadNextMap(Player player) {
        Main.setMap(loadMap(++level, player));
    }

    public static int getLevel() {
        return level;
    }
}
