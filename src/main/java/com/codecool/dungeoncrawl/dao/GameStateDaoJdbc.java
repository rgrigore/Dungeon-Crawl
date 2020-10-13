package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameStateModel;

import javax.sql.DataSource;
import java.util.List;

public class GameStateDaoJdbc implements Dao<GameStateModel> {

    private final DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameStateModel state) {
    }

    @Override
    public void update(GameStateModel state) {

    }

    @Override
    public GameStateModel get(int id) {
        return null;
    }

    @Override
    public List<GameStateModel> getAll() {
        return null;
    }
}
