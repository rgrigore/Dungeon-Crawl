package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements Dao<GameStateModel> {

    private final DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameStateModel state) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO game_state (saved_at, name, player_id, map_id) VALUES (CURRENT_TIMESTAMP, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            setParameters(statement, state);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                state.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameStateModel state) {

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE game_state SET saved_at = CURRENT_TIMESTAMP, name = ?, player_id = ?, map_id = ? WHERE id = ?"
            );

            statement.setInt(4, state.getId());
            setParameters(statement, state);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setParameters(PreparedStatement statement, GameStateModel state) throws SQLException {
        statement.setString(1, state.getName());
        statement.setInt(2, state.getPlayer().getId());
        statement.setInt(3, state.getMap().getId());
    }

    @Override
    public GameStateModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT id, name, saved_at, player_id, map_id FROM game_state WHERE id = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return new GameStateModel(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("saved_at"),
                    resultSet.getInt("player_id"),
                    resultSet.getInt("map_id")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameStateModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT id, name, saved_at, player_id, map_id FROM game_state"
            );

            List<GameStateModel> result = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(new GameStateModel(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("saved_at"),
                        resultSet.getInt("player_id"),
                        resultSet.getInt("map_id")
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



//
//        List<GameStateModel> dummy = new ArrayList<>();
//        dummy.add(new GameStateModel(1, "testul", new Date(1602696806701l), 5, 7));
//        dummy.add(new GameStateModel(2, "testul1", new Date(1602696806701l), 5, 7));
//        dummy.add(new GameStateModel(3, "testul2", new Date(1602696806701l), 5, 7));
//        return dummy;
    }
}
