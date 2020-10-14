package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.MapModel;
import com.codecool.dungeoncrawl.model.MobModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class MapDaoJdbc implements Dao<MapModel> {

    private final DataSource dataSource;

    private final MobDaoJdbc mobDaoJdbc;
    private final ItemDaoJdbc itemDaoJdbc;

    public MapDaoJdbc(DataSource dataSource, Dao<MobModel> mobDao, Dao<ItemModel> itemDao) {
        this.dataSource = dataSource;
        mobDaoJdbc = (MobDaoJdbc) mobDao;
        itemDaoJdbc = (ItemDaoJdbc) itemDao;
    }

    @Override
    public void add(MapModel map) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO map (width, height, terrain) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, map.getWidth());
            statement.setInt(2, map.getHeight());
            statement.setString(3, map.getTerrain());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            map.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MapModel model) {
        mobDaoJdbc.emptyMapMobs(model.getId());
        itemDaoJdbc.emptyMapItems(model.getId());
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
