package com.dgrissom.masslibrary.games.tilemap;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;

public class TileInstance {
    private final Tile tile;
    private final String[] properties;
    private final int x, y;

    public TileInstance(Tile tile, int x, int y) {
        this(tile, x, y, new String[] {});
    }
    public TileInstance(Tile tile, int x, int y, String[] properties) {
        this.tile = tile;
        this.x = x;
        this.y = y;
        this.properties = properties;
    }

    public Tile getTile() {
        return this.tile;
    }
    public String[] getProperties() {
        return this.properties;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public Point2d position(TileMap map) {
        return new Point2d(this.x, this.y).multiply(map.getTileSize()).add(map.getPosition());
    }
    public Point2d center(TileMap map) {
        return position(map).add(map.getTileSize() / 2);
    }

    public boolean hasProperty(String property) {
        for (String prop : this.properties)
            if (prop.equals(property))
                return true;
        return false;
    }
}
