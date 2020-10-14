package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MobModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO mob (type_symbol, map_id, x, y, hp) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, String.valueOf(mob.getSymbol()));
            statement.setInt(2, mob.getMapId());
            statement.setInt(3, mob.getX());
            statement.setInt(4, mob.getY());
            statement.setInt(5, mob.getHp());
            statement.executeUpdate();
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
        return null;
    }

    @Override
    public List<MobModel> getAll() {
        return null;
    }
}
