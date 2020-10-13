package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.util.List;

public class ItemDaoJdbc implements Dao<ItemModel> {

    private final DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel model) {
    }

    @Override
    public void update(ItemModel model) {

    }

    @Override
    public ItemModel get(int id) {
        return null;
    }

    @Override
    public List<ItemModel> getAll() {
        return null;
    }
}
