package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.BaseModel;

import java.util.List;

public interface Dao<T extends BaseModel> {
    void add(T model);
    void update(T model);
    T get(int id);
    List<T> getAll();
}
