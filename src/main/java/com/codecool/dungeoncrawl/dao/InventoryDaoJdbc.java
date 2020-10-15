package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                statement.setInt(1, inventory.getPlayerId());
                statement.setString(2, String.valueOf(item));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(InventoryModel inventory) {
        emptyPlayerInventory(inventory.getPlayerId());
        add(inventory);
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
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT player_id, array_agg(item_symbol) AS items FROM inventory WHERE player_id = ? GROUP BY player_id"
            );
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return new InventoryModel(id, new Character[0]);
            }

            return new InventoryModel(
                    resultSet.getInt("player_id"),
                    Arrays.stream(((String[]) resultSet.getArray("items").getArray())).map(s -> s.charAt(0)).collect(Collectors.toList()).toArray(Character[]::new)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<InventoryModel> getAll() {
        return null;
    }
}
