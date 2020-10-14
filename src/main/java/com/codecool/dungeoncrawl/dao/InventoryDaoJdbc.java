package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryModel;
import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class InventoryDaoJdbc implements Dao<InventoryModel> {

    private final DataSource dataSource;

    public InventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(InventoryModel inventory) {
        try (Connection conn = dataSource.getConnection()) {
            for (char item : inventory.getItems()) {
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO inventory (player_id, item_symbol) VALUES (?, ?)"
                );
                statement.setInt(1, inventory.getPlayer_id());
                statement.setString(2, String.valueOf(item));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(InventoryModel inventory) {
        emptyPlayerInventory(inventory.getPlayer_id());
    }

    private void emptyPlayerInventory(int playerID) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM inventory WHERE player_id = ?"
            );
            statement.setInt(1, playerID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InventoryModel get(int id) {
        return null;
    }

    @Override
    public List<InventoryModel> getAll() {
        return null;
    }
}
