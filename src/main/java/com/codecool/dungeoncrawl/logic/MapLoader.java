package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
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
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
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
                        case '@':
                            cell.setType(CellType.PLAYER);
                            map.setPlayer(new Player(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
