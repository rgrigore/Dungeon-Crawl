package com.codecool.dungeoncrawl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Field;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;

@JsonIgnoreProperties(value = { "id" })
public abstract class BaseModel implements SQLData {
    // null means not saved
    protected Integer id = 0;
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
            Object value;
            try {
                value = field.get(this);
                if (value != null) {
                    sb.append(field.getName()).append(":").append(value).append(",");
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
        }
        return sb.toString();
    }

    @Override
    public String getSQLTypeName() {
        return sqlType;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sqlType = typeName;
        id = stream.readInt();
    }
}
