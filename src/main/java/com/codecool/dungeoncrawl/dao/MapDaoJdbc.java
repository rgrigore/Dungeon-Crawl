package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MapModel;

import javax.sql.DataSource;
import java.util.List;

public class MapDaoJdbc implements Dao<MapModel> {

    private final DataSource dataSource;

    public MapDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MapModel model) {
    }

    @Override
    public void update(MapModel model) {

    }

    @Override
    public MapModel get(int id) {
        return null;
    }

    @Override
    public List<MapModel> getAll() {
        return null;
    }
}
