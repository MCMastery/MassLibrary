package com.dgrissom.masslibrary.games.rpg;

import com.dgrissom.masslibrary.games.Game;
import com.dgrissom.masslibrary.games.GameEngine;
import com.dgrissom.masslibrary.games.tilemap.TileMap;

import java.io.IOException;

public class RPG implements Game {
    private static RPG instance;

    private RPG() {}

    @Override
    public void init() {
        try {
            RPGMap.load();

            Player player = new Player();
            GameEngine.getInstance().getStage().setCamera(player);
            GameEngine.getInstance().getStage().add(player);
        } catch (IOException | TileMap.MapFileParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GameEngine.getInstance().launch(instance = new RPG());
    }
    public static RPG getInstance() {
        return instance;
    }
}
