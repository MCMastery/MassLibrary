package com.dgrissom.masslibrary.games.rpg;

import com.dgrissom.masslibrary.games.GameEngine;
import com.dgrissom.masslibrary.games.tilemap.TileInstance;
import com.dgrissom.masslibrary.games.tilemap.TileMap;
import com.dgrissom.masslibrary.games.tilemap.TileSet;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RPGMap {
    private static TileMap map;
    private static TileSet tileSet;

    private RPGMap() {}

    public static TileMap getMap() {
        return map;
    }
    public static TileSet getTileSet() {
        return tileSet;
    }

    public static Point2d getPlayerSpawn() {
        List<TileInstance> tiles = map.fromProperty("player-spawn");
        if (tiles.size() == 0)
            return null;
        return tiles.get(0).center(map);
    }

    public static void load() throws IOException, TileMap.MapFileParseException {
        tileSet = TileSet.load(new File("res/games/rpg/tile-set"), "{id}.png");
        map = TileMap.load(new File("res/games/rpg/test.map"), tileSet);
        GameEngine.getInstance().getStage().add(map);
    }
}
