package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.MobModel;

import javax.sql.DataSource;
import java.util.List;

public class MobDaoJdbc implements Dao<MobModel> {

    private final DataSource dataSource;

    public MobDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(MobModel model) {
    }

    @Override
    public void update(MobModel model) {

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
