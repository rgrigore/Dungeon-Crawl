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
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO player (name, max_hp, hp, attack, x, y) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            setParameters(statement, player);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                player.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE player SET name = ?, max_hp = ?, hp = ?, attack = ?, x = ?, y = ? WHERE id = ?"
            );

            statement.setInt(7, player.getId());
            setParameters(statement, player);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setParameters(PreparedStatement statement, PlayerModel player) throws SQLException {
        statement.setString(1, player.getName());
        statement.setInt(2, player.getMaxHp());
        statement.setInt(3, player.getHp());
        statement.setInt(4, player.getAttack());
        statement.setInt(5, player.getX());
        statement.setInt(6, player.getY());
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
