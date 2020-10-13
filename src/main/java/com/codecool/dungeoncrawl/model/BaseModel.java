package com.codecool.dungeoncrawl.model;

import java.lang.reflect.Field;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;

public abstract class BaseModel implements SQLData {
    // null means not saved
    protected Integer id;
    private String sqlType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(this);
                if (value != null) {
                    sb.append(field.getName() + ":" + value + ",");
                }
            } catch (IllegalAccessException e) {

            }
        }
        return sb.toString();
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return sqlType;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sqlType = typeName;
        id = stream.readInt();
    }
}
