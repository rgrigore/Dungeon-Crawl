package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDaoJdbc implements Dao<ItemModel> {

    private final DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel item) {
        try (Connection conn = dataSource.getConnection()) {
            while (item != null) {
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO item (type_symbol, map_id, x, y) VALUES (?, ?, ?, ?)"
                );
                statement.setString(1, String.valueOf(item.getSymbol()));
                statement.setInt(2, item.getMapId());
                statement.setInt(3, item.getX());
                statement.setInt(4, item.getY());
                statement.executeUpdate();
                item = item.getNext();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ItemModel model) {

    }

    public void emptyMapItems(int mapID) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM item WHERE map_id = ?"
            );
            statement.setInt(1, mapID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT type_symbol, x, y FROM item WHERE map_id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            ItemModel head = new ItemModel(
                    resultSet.getString("type_symbol").charAt(0),
                    resultSet.getInt("x"),
                    resultSet.getInt("y")
            );
            ItemModel last = head;
            while (resultSet.next()) {
                ItemModel newItem = new ItemModel(
                        resultSet.getString("type_symbol").charAt(0),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")
                );
                last.setNext(newItem);
                last = newItem;
            }
            return head;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemModel> getAll() {
        return null;
    }
}
