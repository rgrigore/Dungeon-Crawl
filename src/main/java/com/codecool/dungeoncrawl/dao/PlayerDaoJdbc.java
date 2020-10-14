package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PlayerDaoJdbc implements Dao<PlayerModel> {
    private final DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (name, max_hp, hp, attack, x, y) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getName());
            statement.setInt(2, player.getMax_hp());
            statement.setInt(3, player.getHp());
            statement.setInt(4, player.getAttack());
            statement.setInt(5, player.getX());
            statement.setInt(6, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    @Override
    public PlayerModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT id, name, max_hp, hp, attack, x, y FROM player WHERE id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new PlayerModel(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("max_hp"),
                    resultSet.getInt("hp"),
                    resultSet.getInt("attack"),
                    resultSet.getInt("x"),
                    resultSet.getInt("y")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }
}
