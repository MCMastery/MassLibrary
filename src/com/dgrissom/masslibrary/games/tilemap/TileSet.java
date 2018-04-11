package com.dgrissom.masslibrary.games.tilemap;

import com.dgrissom.masslibrary.Random;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TileSet {
    private List<Tile> tiles;

    public TileSet() {
        this.tiles = new ArrayList<>();
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }
    public Tile get(int id) {
        return this.tiles.get(id);
    }
    public void add(Tile tile) {
        this.tiles.add(tile);
    }

    public Tile random() {
        return get(Random.random(this.tiles.size()));
    }

    // loads a tileset from a folder
    // nameScheme example: {id}.png
    // or tile-{id}.png
    // id's start at 0 and must be numbered consecutively
    public static TileSet load(File folder, String nameScheme) throws IOException {
        TileSet tileSet = new TileSet();
        // end loop once we reach an id whose associated file does not exist
        for (int id = 0; ; id++) {
            File tileFile = new File(folder, nameScheme.replace("{id}", String.valueOf(id)));
            if (!tileFile.exists())
                break;
            tileSet.add(Tile.load(tileFile));
        }
        return tileSet;
    }
}
