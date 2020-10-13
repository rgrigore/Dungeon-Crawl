package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.GameMap;
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
        mapModelDao = new MapDaoJdbc(dataSource);
        mobModelDao = new MobDaoJdbc(dataSource);
        itemModelDao = new ItemDaoJdbc(dataSource);
    }

    public void saveGame(GameMap gameMap) {
        // TODO Display a list of saves
        if (true) {
            // Player clicks <NEW> -> insert
            String name = "test"; // the name of the new save

            GameStateModel gameStateModel = new GameStateModel(name, gameMap);
        } else {
            // Player selects a save -> update
            int id = 0; // id of selected save
            GameStateModel gameStateModel = gameStateModelDao.get(id);
        }
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerModelDao.add(model);
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
