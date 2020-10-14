package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

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
        mobModelDao = new MobDaoJdbc(dataSource);
        itemModelDao = new ItemDaoJdbc(dataSource);
        mapModelDao = new MapDaoJdbc(dataSource, mobModelDao, itemModelDao);
    }

    public void saveGame(GameMap gameMap) {
        // TODO Display a list of saves
        if (true) {
            // Player clicks <NEW> -> insert
            String name = "test"; // the name of the new save

            GameStateModel gameStateModel = new GameStateModel(name, gameMap);

            playerModelDao.add(gameStateModel.getPlayer());
            inventoryModelDao.add(gameStateModel.getPlayer().getInventory());

            mapModelDao.add(gameStateModel.getMap());
            mobModelDao.add(gameStateModel.getMap().getMobModels());
            itemModelDao.add(gameStateModel.getMap().getItemModels());

            gameStateModelDao.add(gameStateModel);
        } else {
            // Player selects a save -> update
            int id = 0; // id of selected save
            GameStateModel gameStateModel = gameStateModelDao.get(id);
        }
    }

    public void loadGame() {
        // TODO Display a list of game_state-s
        int saveID = 1; // TODO Get selected game_state id
        GameStateModel gameStateModel = gameStateModelDao.get(saveID);

        gameStateModel.setPlayer(playerModelDao.get(gameStateModel.getPlayerID()));
        gameStateModel.getPlayer().setInventory(inventoryModelDao.get(gameStateModel.getPlayerID()));

        gameStateModel.setMap(mapModelDao.get(gameStateModel.getMapID()));
        gameStateModel.getMap().setMobModels(mobModelDao.get(gameStateModel.getMapID()));
        gameStateModel.getMap().setItemModels(itemModelDao.get(gameStateModel.getMapID()));

        MapLoader.loadMap(gameStateModel.getMap(), gameStateModel.getPlayer());
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
