package com.dgrissom.masslibrary.games;

public class GameLoop {
    private GameLoop() {}

    public static void run() {
        for (GameActor actor : GameEngine.getInstance().getStage())
            actor.update();
        GameRenderer.getInstance().render(GameEngine.getInstance().getStage());
    }
}
