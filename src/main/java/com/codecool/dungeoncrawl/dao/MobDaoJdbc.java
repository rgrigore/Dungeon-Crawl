package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MobModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MobDaoJdbc implements Dao<MobModel> {

    private final DataSource dataSource;

    public MobDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MobModel mob) {
        try (Connection conn = dataSource.getConnection()) {
            while (mob != null) {
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO mob (type_symbol, map_id, x, y, hp) VALUES (?, ?, ?, ?, ?)"
                );
                statement.setString(1, String.valueOf(mob.getSymbol()));
                statement.setInt(2, mob.getMapId());
                statement.setInt(3, mob.getX());
                statement.setInt(4, mob.getY());
                statement.setInt(5, mob.getHp());
                statement.executeUpdate();
                mob = mob.getNext();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MobModel model) {

    }

    public void emptyMapMobs(int mapID) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "DELETE FROM mob WHERE map_id = ?"
            );
            statement.setInt(1, mapID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MobModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT type_symbol, hp, x, y FROM mob WHERE map_id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            MobModel head = new MobModel(
                    resultSet.getString("type_symbol").charAt(0),
                    resultSet.getInt("hp"),
                    resultSet.getInt("x"),
                    resultSet.getInt("y")
            );
            MobModel last = head;
            while (resultSet.next()) {
                MobModel newMob = new MobModel(
                        resultSet.getString("type_symbol").charAt(0),
                        resultSet.getInt("hp"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")
                );
                last.setNext(newMob);
                last = newMob;
            }
            return head;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MobModel> getAll() {
        return null;
    }
}
