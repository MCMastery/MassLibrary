package com.dgrissom.masslibrary.games.oats;

import com.dgrissom.masslibrary.games.Game;
import com.dgrissom.masslibrary.games.GameEngine;

public class OatsGame implements Game {
    private static OatsGame INSTANCE;

    private OatsGame() {}

    @Override
    public void init() {

    }

    public static void main(String[] args) {
        INSTANCE = new OatsGame();
        Player player = new Player();
        GameEngine.getInstance().getStage().setCamera(player);
        GameEngine.getInstance().getStage().add(player);
        GameEngine.getInstance().getStage().add(new Background());
        GameEngine.getInstance().launch(INSTANCE);
    }
}
