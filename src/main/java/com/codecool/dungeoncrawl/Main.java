package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.StringJoiner;

public class Main extends Application {
    private static final int HORIZONTAL_VIEW = 25;
    private static final int VERTICAL_VIEW = 21;

    private static final KeyCode playerUp = KeyCode.W;
    private static final KeyCode playerDown = KeyCode.S;
    private static final KeyCode playerLeft = KeyCode.A;
    private static final KeyCode playerRight = KeyCode.D;

    private final MainLoop mainLoop = new MainLoop();
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            HORIZONTAL_VIEW * Tiles.TILE_WIDTH,
            VERTICAL_VIEW * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label playerLabel = new Label();
    Label healthLabel = new Label();
    Label damage = new Label();
    Label inventory = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Player: "), 0, 0);
        playerLabel.setFont(new Font("Bold", 18));
        playerLabel.setTextFill(Color.web("#33FF58"));
        ui.add(playerLabel, 1, 0);
        ui.add(new Label(""), 0, 1);
        ui.add(new Label("Health: "), 0, 2);
        ui.add(healthLabel, 1, 2);
        ui.add(new Label("Damage: "), 0, 3);
        ui.add(damage, 1, 3);
        ui.add(new Label("Inventory: "), 0, 4);
        ui.add(inventory, 0, 5);
        ui.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));


        // create a text input dialog
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Character name");
        td.setHeaderText(null);
        td.setGraphic(null);
        td.setContentText("Please insert player name");

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();

        td.showAndWait();
        map.getPlayer().setName(td.getEditor().getText());

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

    private void refresh() {
        int leftOffset = Math.max(map.getPlayer().getX() - Math.floorDiv(HORIZONTAL_VIEW, 2), 0);
        int rightOffset = Math.min(map.getPlayer().getX() + (HORIZONTAL_VIEW - (map.getPlayer().getX() - leftOffset)), map.getWidth());
        leftOffset = Math.max(leftOffset - (HORIZONTAL_VIEW - (rightOffset - leftOffset)), 0);

        int upOffset = Math.max(map.getPlayer().getY() - Math.floorDiv(VERTICAL_VIEW, 2), 0);
        int downOffset = Math.min(map.getPlayer().getY() + (VERTICAL_VIEW - (map.getPlayer().getY() - upOffset)), map.getHeight());
        upOffset = Math.max(upOffset - (VERTICAL_VIEW - (downOffset - upOffset)), 0);

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = leftOffset; x < rightOffset; x++) {
            for (int y = upOffset; y < downOffset; y++) {
                Drawable drawable;
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    drawable = cell.getActor();
                } else if (cell.getItem()!=null) {
                    drawable = cell.getItem();
                }else {
                    drawable = cell;
                }
                Tiles.drawTile(context, drawable, x - leftOffset, y - upOffset);
            }
        }
        playerLabel.setText(map.getPlayer().getName());
        healthLabel.setText(map.getPlayer().getCurrentHealth() + "/" + map.getPlayer().getHealth());
        damage.setText("" + map.getPlayer().getDamage());
        StringJoiner stringJoiner = new StringJoiner("\n", "", "");
        for (String item : map.getPlayer().getInventory()) {
            stringJoiner.add(String.format("  - %s", item));
        }
        inventory.setText(stringJoiner.toString());
    }

    private class MainLoop extends AnimationTimer {
        @Override
        public void handle(long l) {
            refresh();
        }
    }
}
