package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.model.GameStateModel;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class Main extends Application {
    private static final int HORIZONTAL_VIEW = 25;
    private static final int VERTICAL_VIEW = 21;

    private static final KeyCode playerUp = KeyCode.W;
    private static final KeyCode playerDown = KeyCode.S;
    private static final KeyCode playerLeft = KeyCode.A;
    private static final KeyCode playerRight = KeyCode.D;

    private final MainLoop mainLoop = new MainLoop();
    private static GameMap map;
    private static GameMap newMap;
    Canvas canvas = new Canvas(
            HORIZONTAL_VIEW * Tiles.TILE_WIDTH,
            VERTICAL_VIEW * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label playerLabel = new Label();
    Label level = new Label();
    Label healthLabel = new Label();
    Label damage = new Label();
    Label inventory = new Label();
    private static final GameDatabaseManager dbManager = new GameDatabaseManager();
    private static final Image gameLogo = new Image("/DC-Logo-2.png");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setupDbManager();
        map = MapLoader.loadMap(1, null);
        newMap = map;
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        Button restartButton = new Button();
        restartButton.setText("Restart");
        restartButton.setOnAction(e -> {MapLoader.restartGame(); setPlayerName();});

        ui.add(restartButton, 0,0);
        ui.add(new Label("Player: "), 0, 1);
        playerLabel.setFont(new Font("Bold", 18));
        playerLabel.setTextFill(Color.web("#33FF58"));
        ui.add(playerLabel, 1, 1);
        ui.add(new Label(""), 0, 2);
        ui.add(new Label("Level: "), 0, 3);
        ui.add(level, 1, 3);
        ui.add(new Label("Health: "), 0, 4);
        ui.add(healthLabel, 1, 4);
        ui.add(new Label("Damage: "), 0, 5);
        ui.add(damage, 1, 5);
        ui.add(new Label("Inventory: "),0, 6);
        ui.add(inventory, 0, 7);
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

        setPlayerName();

        mainLoop.start();
    }

    private void setPlayerName() {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Character name");
        td.setHeaderText(null);
        td.setGraphic(null);
        td.setContentText("Please insert player name");

        // Get the Stage.
        Stage stage = (Stage) td.getDialogPane().getScene().getWindow();

        // Add a custom icon.
        stage.getIcons().add(gameLogo);

        td.showAndWait();
        map.getPlayer().setName(td.getEditor().getText());
    }

    public static void setMap(GameMap newMap) {
        Main.newMap = newMap;
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
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
        } else if (keyCode == KeyCode.F5) {
            dbManager.saveGame();
        } else if (keyCode == KeyCode.F9) {
            dbManager.loadGame();
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
                if (cell != null) {
                    if (cell.getActor() != null) {
                        drawable = cell.getActor();
                    } else if (cell.getItem() != null) {
                        drawable = cell.getItem();
                    } else {
                        drawable = cell;
                    }
                    Tiles.drawTile(context, drawable, x - leftOffset, y - upOffset);
                }
            }
        }
        playerLabel.setText(map.getPlayer().getName());
        level.setText(""+MapLoader.getLevel());
        healthLabel.setText(map.getPlayer().getCurrentHealth() + "/" + map.getPlayer().getHealth());
        damage.setText("" + map.getPlayer().getDamage());
        StringJoiner stringJoiner = new StringJoiner("\n", "", "");
        for (String item : map.getPlayer().getInventoryDisplay()) {
            stringJoiner.add(String.format("  - %s", item));
        }
        inventory.setText(stringJoiner.toString());

        map = newMap;
    }

    private void setupDbManager() {
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    public static void showSaveOptions(List<GameStateModel> gameStateModels) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy:hh:mm");

        List<String> saves = new ArrayList<>();
        saves.add("New save");
        gameStateModels.forEach(gameModel ->
                saves.add(String.format(
                        "%s : %s",
                        gameModel.getName(),
                        dateFormat.format(gameModel.getSavedAt())
                ))
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(saves.get(0), saves);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Save options");
        dialog.setContentText("Select: ");

        // Get the Stage.
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        // Add a custom icon.
        stage.getIcons().add(gameLogo);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            int index = saves.indexOf(result.get());
            if(index == 0) {
                TextInputDialog td = new TextInputDialog();
                td.setTitle("New save");
                td.setHeaderText(null);
                td.setGraphic(null);
                td.setContentText("Save name:");
                String name;
                boolean ok = true;
                do {
                    td.showAndWait();
                    name = td.getEditor().getText();
                    for (GameStateModel gameStateModel : gameStateModels) {
                        if (name.equals(gameStateModel.getName())) {
                            ok = false;
                            break;
                        }
                    }
                } while (!ok);
                dbManager.saveNewGame(map, name);
            } else {
                dbManager.saveOldGame(map, gameStateModels.get(index - 1));
            }
        }
    }

    public static void showLoadOptions(List<GameStateModel> gameStateModels) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy:hh:mm");

        List<String> saves = new ArrayList<>();
        gameStateModels.forEach(gameModel ->
                saves.add(String.format(
                        "%s : %s",
                        gameModel.getName(),
                        dateFormat.format(gameModel.getSavedAt())
                ))
        );
        ChoiceDialog<String> dialog = new ChoiceDialog<>(saves.size() > 0 ? saves.get(0) : "empty", saves);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Load options");
        dialog.setContentText("Select: ");

        // Get the Stage.
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        // Add a custom icon.
        stage.getIcons().add(gameLogo);

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(option -> {
            if (!option.equals("empty")) {
                dbManager.loadGameState(gameStateModels.get(saves.indexOf(option)));
            }
        });
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

    private class MainLoop extends AnimationTimer {
        @Override
        public void handle(long l) {
            refresh();
        }
    }
}
