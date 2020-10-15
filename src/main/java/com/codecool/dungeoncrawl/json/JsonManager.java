package com.codecool.dungeoncrawl.json;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.model.GameStateModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonManager {

    private static final String SAVE_PATH = "saves/";
    private final ObjectMapper objectMapper;

    public JsonManager() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String getSavePath() {
        return SAVE_PATH;
    }

    public void exportJson(GameMap gameMap, String name) {
        GameStateModel gameStateModel = new GameStateModel(name, gameMap);
        gameStateModel.setSavedAt(new java.util.Date());


        try {
            if (!Files.exists(Path.of(SAVE_PATH))) {
                if (!new File(SAVE_PATH).mkdir()) {
                    System.out.println("Failed to create saves folder.");
                    return;
                }
            }
            objectMapper.writeValue(new File(String.format("%s%s.json", SAVE_PATH, name)), gameStateModel);
        } catch (IOException e) {
            System.out.printf("Failed to save %s.json%n", name);
            e.printStackTrace();
        }
    }

    public void importJson(String file) {
        try {
            File save = new File(String.format("%s%s", SAVE_PATH, file));

            GameStateModel gameStateModel = objectMapper.readValue(save, GameStateModel.class);
            MapLoader.loadMap(gameStateModel.getMap(), gameStateModel.getPlayer());
        } catch (IOException e) {
            System.out.printf("Failed to read file: %s", file);
            e.printStackTrace();
        }
    }
}
