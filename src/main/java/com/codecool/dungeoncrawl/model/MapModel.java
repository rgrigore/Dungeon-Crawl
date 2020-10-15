package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class MapModel extends BaseModel implements SQLData {
    private int level;
    private int width;
    private int height;
    private String terrain;
    private MobModel mobModels = null;
    private ItemModel itemModels = null;

    public MapModel() {
    }

    public MapModel(int id, int level, int width, int height, String terrain) {
        this.id = id;
        this.level = level;
        this.width = width;
        this.height = height;
        this.terrain = terrain;

    }

    public MapModel(GameMap gameMap) {
        this.level = gameMap.getLevel();
        this.width = gameMap.getWidth();
        this.height = gameMap.getHeight();

        StringBuilder terrainBuilder = new StringBuilder();

        MobModel lastMob = null;
        ItemModel lastItem = null;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = gameMap.getCell(x, y);
                terrainBuilder.append(cell.getSymbol());

                if (cell.getActor() != null && cell.getType() != CellType.PLAYER) {
                    if (lastMob == null) {
                        mobModels = new MobModel(cell.getActor());
                        lastMob = mobModels;
                    } else {
                        lastMob.setNext(lastMob = new MobModel(cell.getActor()));
                    }
                }
                if (cell.getItem() != null) {
                    if (lastItem == null) {
                        itemModels = new ItemModel(cell.getItem());
                        lastItem = itemModels;
                    } else {
                        lastItem.setNext(lastItem = new ItemModel(cell.getItem()));
                    }
                }
            }
        }

        this.terrain = terrainBuilder.toString();
    }

    //region Setters/Getters
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public MobModel getMobModels() {
        return mobModels;
    }

    public void setMobModels(MobModel mobModels) {
        this.mobModels = mobModels;
        if (this.mobModels != null) {
            this.mobModels.setMapId(id);
        }
    }

    public ItemModel getItemModels() {
        return itemModels;
    }

    public void setItemModels(ItemModel itemModels) {
        this.itemModels = itemModels;
        if (this.itemModels != null) {
            this.itemModels.setMapId(id);
        }
    }

    //endregion


    @Override
    public void setId(int id) {
        super.setId(id);
        mobModels.setMapId(id);
        itemModels.setMapId(id);
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        super.readSQL(stream, typeName);
        width = stream.readInt();
        height = stream.readInt();
        terrain = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(width);
        stream.writeInt(height);
        stream.writeString(terrain);
    }
}
