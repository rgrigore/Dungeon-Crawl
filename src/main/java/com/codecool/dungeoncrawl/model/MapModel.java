package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class MapModel extends BaseModel implements SQLData {
    private int width;
    private int height;
    private String terrain;
    private MobModel[] mobModels;
    private ItemModel[] itemModels;

    public MapModel(int width, int height, String terrain, MobModel[] mobModels, ItemModel[] itemModels) {
        this.width = width;
        this.height = height;
        this.terrain = terrain;
        this.mobModels = mobModels;
        this.itemModels = itemModels;
    }

    public MapModel(GameMap gameMap) {
        this.width = gameMap.getWidth();
        this.height = gameMap.getHeight();

        StringBuilder terrainBuilder = new StringBuilder();
        ArrayList<MobModel> mobModels = new ArrayList<>();
        ArrayList<ItemModel> itemModels = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = gameMap.getCell(x, y);
                terrainBuilder.append(cell.getSymbol());
                if (cell.getActor() != null && cell.getType() != CellType.PLAYER) {
                    mobModels.add(new MobModel(cell.getActor()));
                }
                if (cell.getItem() != null) {
                    itemModels.add(new ItemModel(cell.getItem()));
                }
            }
        }

        this.terrain = terrainBuilder.toString();
        this.mobModels = mobModels.toArray(MobModel[]::new);
        this.itemModels = itemModels.toArray(ItemModel[]::new);
    }

    //region Setters/Getters
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

    public MobModel[] getMobModels() {
        return mobModels;
    }

    public void setMobModels(MobModel[] mobModels) {
        this.mobModels = mobModels;
    }

    public ItemModel[] getItemModels() {
        return itemModels;
    }

    public void setItemModels(ItemModel[] itemModels) {
        this.itemModels = itemModels;
    }

    //endregion

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
