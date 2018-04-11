package com.dgrissom.masslibrary.games;

import com.dgrissom.masslibrary.FXUtils;
import com.dgrissom.masslibrary.games.input.Input;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class GameEngine extends Application {
    private static final double FPS = 60;
    private static final GameEngine instance = new GameEngine();
    private static final String GAME_SCENE_PATH = "res/game-scene.fxml";
    private static Game game;
    private GameStage stage;
    private Scene scene;

    public GameEngine() {
        this.stage = new GameStage();
        this.scene = null;
    }

    public GameStage getStage() {
        return this.stage;
    }
    public Scene getScene() {
        return this.scene;
    }

    public void launch(Game game) {
        GameEngine.game = game;
        Application.launch();
    }
    private void startLoop() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000 / FPS),
                ae -> GameLoop.run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.scene = new Scene(FXMLLoader.load(GameEngine.class.getResource(GAME_SCENE_PATH)));
        GameRenderer.getInstance().bindCanvasSize(this.scene);
        primaryStage.setScene(this.scene);
        FXUtils.fullscreen(primaryStage);
        primaryStage.show();

        Input.init();
        startLoop();
        game.init();
    }

    public static GameEngine getInstance() {
        return instance;
    }
    public static Game getGame() {
        return game;
    }
}
