package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static final KeyCode playerUp = KeyCode.W;
    private static final KeyCode playerDown = KeyCode.S;
    private static final KeyCode playerLeft = KeyCode.A;
    private static final KeyCode playerRight = KeyCode.D;

    private final MainLoop mainLoop = new MainLoop();
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();

    int playerX, playerY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        mainLoop.start();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();

        if (keyCode == playerUp) {
            map.getPlayer().move(0, -1);
        } else if (keyCode == playerDown) {
            map.getPlayer().move(0, 1);
        } else if (keyCode == playerLeft) {
            map.getPlayer().move(-1, 0);
        } else if (keyCode == playerRight) {
            map.getPlayer().move(1,0);
        }
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == playerUp || keyEvent.getCode() == playerDown ||
                keyEvent.getCode() == playerLeft || keyEvent.getCode() == playerRight) {
            playerX = 0;
            playerY = 0;
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText(map.getPlayer().getCurrentHealth() + "/" + map.getPlayer().getHealth());
    }

    private class MainLoop extends AnimationTimer {
        @Override
        public void handle(long l) {
            map.getPlayer().move(playerX, playerY);
            refresh();
        }
    }
}
