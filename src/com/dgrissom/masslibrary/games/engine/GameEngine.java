package com.dgrissom.masslibrary.games.engine;

import com.dgrissom.masslibrary.games.testing.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class GameEngine extends Application {
    private static GameEngine instance = null;
    private static Game game = null;
    private CanvasController canvasController;
    private GameClock clock;
    private Scene scene;
    private List<GameObject> objects;

    public CanvasController getCanvasController() {
        return this.canvasController;
    }
    public void setCanvasController(CanvasController canvasController) {
        this.canvasController = canvasController;
    }
    public GameClock getClock() {
        return this.clock;
    }
    public Scene getScene() {
        return this.scene;
    }
    public List<GameObject> getObjects() {
        return this.objects;
    }
    public void add(GameObject... objects) {
        this.objects.addAll(Arrays.asList(objects));
    }
    public void remove(GameObject... objects) {
        this.objects.removeAll(Arrays.asList(objects));
    }
    public void sortObjects() {
        this.objects.sort(Comparator.comparingInt(GameObject::getLayer));
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (instance != null)
            throw new UnsupportedOperationException("engine is already running");
        instance = this;
        this.objects = new ArrayList<>();

        Parent root = FXMLLoader.load(getClass().getResource("res/scene.fxml"));
        this.scene = new Scene(root);
        this.canvasController.bindDimensions(this.scene);
        stage.setTitle("GameEngine");
        stage.setScene(this.scene);
        stage.show();

        this.clock = new GameClock(60);
        this.clock.start();

        game.init();
    }

    public static void start(Game game) {
        GameEngine.game = game;
        Application.launch(GameEngine.class);
    }

    public static GameEngine getInstance() {
        return instance;
    }
}
