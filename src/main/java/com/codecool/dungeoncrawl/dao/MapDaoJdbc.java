package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MapModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class MapDaoJdbc implements Dao<MapModel> {

    private final DataSource dataSource;

    public MapDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MapModel map) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO map (level, width, height, terrain) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            setParameters(statement, map);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                map.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MapModel map) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE map SET level = ?, width = ?, height = ?, terrain = ? WHERE id = ?"
            );

            statement.setInt(5, map.getId());
            setParameters(statement, map);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setParameters(PreparedStatement statement, MapModel map) throws SQLException {
        statement.setInt(1, map.getLevel());
        statement.setInt(2, map.getWidth());
        statement.setInt(3, map.getHeight());
        statement.setString(4, map.getTerrain());
    }

    @Override
    public MapModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT id, level, width, height, terrain FROM map WHERE id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new MapModel(
                    resultSet.getInt("id"),
                    resultSet.getInt("level"),
                    resultSet.getInt("width"),
                    resultSet.getInt("height"),
                    resultSet.getString("terrain")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MapModel> getAll() {
        return null;
    }
}
