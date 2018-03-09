package com.dgrissom.masslibrary.games.engine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameClock {
    private final Timeline timeline;

    public GameClock(double fps) {
        this.timeline = new Timeline(new KeyFrame(
                Duration.millis(1000.0 / fps),
                ae -> tick()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void tick() {
        GameEngine.getInstance().sortObjects();
        for (GameObject object : GameEngine.getInstance().getObjects())
            object.update();
        GameEngine.getInstance().getCanvasController().render();
    }

    public void start() {
        this.timeline.play();
    }
    public void stop() {
        this.timeline.stop();
    }
}
