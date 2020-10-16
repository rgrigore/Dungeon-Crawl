package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GameDatabaseManager {

    private Dao<GameStateModel> gameStateModelDao;
    private Dao<PlayerModel> playerModelDao;
    private Dao<InventoryModel> inventoryModelDao;
    private Dao<MapModel> mapModelDao;
    private Dao<MobModel> mobModelDao;
    private Dao<ItemModel> itemModelDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        gameStateModelDao = new GameStateDaoJdbc(dataSource);
        playerModelDao = new PlayerDaoJdbc(dataSource);
        inventoryModelDao = new InventoryDaoJdbc(dataSource);
        mapModelDao = new MapDaoJdbc(dataSource);
        mobModelDao = new MobDaoJdbc(dataSource);
        itemModelDao = new ItemDaoJdbc(dataSource);
    }

    public void saveGame() {
        List<GameStateModel> gameStateModels = gameStateModelDao.getAll();
        Main.showSaveOptions(gameStateModels);
    }

    public void saveNewGame(GameMap gameMap, String name) {
        GameStateModel gameStateModel = new GameStateModel(name, gameMap);

        playerModelDao.add(gameStateModel.getPlayer());
        inventoryModelDao.add(gameStateModel.getPlayer().getInventory());

        mapModelDao.add(gameStateModel.getMap());
        mobModelDao.add(gameStateModel.getMap().getMobModels());
        itemModelDao.add(gameStateModel.getMap().getItemModels());

        gameStateModelDao.add(gameStateModel);
    }

    public void saveOldGame(GameMap gameMap, GameStateModel gameStateModel) {
        loadGameData(gameStateModel);
        updatePlayer(gameStateModel.getPlayer(), gameMap.getPlayer());
        updateMap(gameStateModel.getMap(), gameMap);

        playerModelDao.update(gameStateModel.getPlayer());
        inventoryModelDao.update(gameStateModel.getPlayer().getInventory());

        mapModelDao.update(gameStateModel.getMap());
        mobModelDao.update(gameStateModel.getMap().getMobModels());
        itemModelDao.update(gameStateModel.getMap().getItemModels());

        gameStateModelDao.update(gameStateModel);
    }

    private void updatePlayer(PlayerModel playerModel, Player player) {
        InventoryModel inventoryModel = new InventoryModel(player.getInventory());
        inventoryModel.setPlayerId(playerModel.getId());
        playerModel.setInventory(inventoryModel);
        playerModel.setName(player.getName());
        playerModel.setMaxHp(player.getHealth());
        playerModel.setHp(player.getCurrentHealth());
        playerModel.setAttack(player.getDamage());
        playerModel.setX(player.getX());
        playerModel.setY(player.getY());
    }

    private void updateMap(MapModel mapModel, GameMap gameMap) {
        StringBuilder terrain = new StringBuilder();

        MobModel headMob = null;
        MobModel lastMob = null;
        ItemModel headItem = null;
        ItemModel lastItem = null;

        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(); x++) {
                Cell cell = gameMap.getCell(x, y);
                terrain.append(cell.getSymbol());

                if (cell.getActor() != null && cell.getType() != CellType.PLAYER) {
                    if (lastMob == null) {
                        headMob = new MobModel(cell.getActor());
                        lastMob = headMob;
                    } else {
                        lastMob.setNext(lastMob = new MobModel(cell.getActor()));
                    }
                    lastMob.setMapId(mapModel.getId());
                }
                if (cell.getItem() != null) {
                    if (lastItem == null) {
                        headItem = new ItemModel(cell.getItem());
                        lastItem = headItem;
                    } else {
                        lastItem.setNext(lastItem = new ItemModel(cell.getItem()));
                    }
                    lastItem.setMapId(mapModel.getId());
                }
            }
        }

        mapModel.setTerrain(terrain.toString());
        mapModel.setMobModels(headMob);
        mapModel.setItemModels(headItem);
        mapModel.setLevel(gameMap.getLevel());
        mapModel.setWidth(gameMap.getWidth());
        mapModel.setHeight(gameMap.getHeight());
    }

    public void loadGame() {
        List<GameStateModel> gameStateModels = gameStateModelDao.getAll();
        Main.showLoadOptions(gameStateModels);
    }

    public void loadGameState(GameStateModel gameStateModel) {
        loadGameData(gameStateModel);

        MapLoader.loadMap(gameStateModel.getMap(), gameStateModel.getPlayer());
    }

    private void loadGameData(GameStateModel gameStateModel) {
        gameStateModel.setPlayer(playerModelDao.get(gameStateModel.getPlayerId()));
        gameStateModel.getPlayer().setInventory(inventoryModelDao.get(gameStateModel.getPlayerId()));

        gameStateModel.setMap(mapModelDao.get(gameStateModel.getMapId()));
        gameStateModel.getMap().setMobModels(mobModelDao.get(gameStateModel.getMapId()));
        gameStateModel.getMap().setItemModels(itemModelDao.get(gameStateModel.getMapId()));
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        String dbName = "dungeon_crawl";
        String user = "codecool";
        String password = "123";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
