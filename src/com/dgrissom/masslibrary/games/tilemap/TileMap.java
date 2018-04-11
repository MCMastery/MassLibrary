package com.dgrissom.masslibrary.games.tilemap;

import com.dgrissom.masslibrary.files.TextFile;
import com.dgrissom.masslibrary.games.GameActor;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.Canvas;
import com.dgrissom.masslibrary.rendering.Image;
import javafx.embed.swing.SwingFXUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileMap implements GameActor {
    private final int rows, columns, tileSize;
    private final TileInstance[] tiles;
    private Point2d position;
    private javafx.scene.image.Image image;

    public TileMap(int rows, int columns, int tileSize) {
        this.rows = rows;
        this.columns = columns;
        this.tileSize = tileSize;
        this.tiles = new TileInstance[rows * columns];
        this.position = new Point2d(-width() / 2, -height() / 2);
        this.image = null;
    }

    public int getRows() {
        return this.rows;
    }
    public int getColumns() {
        return this.columns;
    }
    public int getTileSize() {
        return this.tileSize;
    }

    public Point2d getPosition() {
        return this.position;
    }
    public void setPosition(Point2d position) {
        this.position = position;
    }
    public int width() {
        return this.columns * this.tileSize;
    }
    public int height() {
        return this.rows * this.tileSize;
    }
    public Rectangle2d bounds() {
        return new Rectangle2d(width(), height());
    }

    private int index(int row, int column) {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns)
            throw new IndexOutOfBoundsException();
        return row * this.columns + column;
    }
    // (y, x) because index() requires row, column
    public TileInstance get(int x, int y) {
        return this.tiles[index(y, x)];
    }
    public void set(int x, int y, Tile tile) {
        set(x, y, tile, new String[] {});
    }
    public void set(int x, int y, Tile tile, String[] properties) {
        // (y, x) because index() requires row, column
        this.tiles[index(y, x)] = new TileInstance(tile, x, y, properties);
        this.image = null;
    }

    // returns all tiles with given property
    public List<TileInstance> fromProperty(String property) {
        List<TileInstance> tiles = new ArrayList<>();
        for (TileInstance tile : this.tiles)
            if (tile.hasProperty(property))
                tiles.add(tile);
        return tiles;
    }

    private void generateImage() {
        this.image = null;
        Image image = new Image(this.columns * this.tileSize, this.rows * this.tileSize);
        for (int x = 0; x < this.columns; x++)
            for (int y = 0; y < this.rows; y++)
                get(x, y).getTile().render(x, y, image);
        this.image = SwingFXUtils.toFXImage(image.getImage(), null);
    }

    @Override
    public int getLayer() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void update() {

    }
    @Override
    public void render(Canvas canvas) {
        if (this.image == null)
            generateImage();
        canvas.draw(this.image, this.position);
    }



    // something went wrong when parsing the map file!
    public static class MapFileParseException extends Exception {
        public MapFileParseException(String msg) {
            super(msg);
        }
    }
    // as a text file, blank lines allowed
    // rows,cols,tileSize must come before tile data
    // lines which start with "#" are comments

    // example:
    // 5,4,16
    //
    // 1 1 1 1
    // 1 0 0 1
    // 1 0 0 1
    // 1 0 0 1
    // 1 1 1 1

    // tiles can also have properties associated with them
    // and can be used, for example, to indicate a spawn position
    // example: 43,spawn,test1,t2
    // tile is id 43 and has properties "spawn", "test1", and "t2"
    // properties are not allowed to have spaces or commas
    public static TileMap load(File file, TileSet tileSet) throws IOException, MapFileParseException {
        TextFile textFile = TextFile.load(file);

        TileMap tileMap = null;
        int y = 0;
        for (int i = 0; i < textFile.getLines().size(); i++) {
            String line = textFile.getLine(i);
            if (line.isEmpty() || line.startsWith("#"))
                continue;

            if (tileMap == null) {
                //rows,cols,tileSize
                String[] mapInfo = line.split(",");
                if (mapInfo.length != 3)
                    throw new MapFileParseException("nonsensical line " + line);

                try {
                    int rows = Integer.parseInt(mapInfo[0]);
                    int cols = Integer.parseInt(mapInfo[1]);
                    int tileSize = Integer.parseInt(mapInfo[2]);
                    tileMap = new TileMap(rows, cols, tileSize);
                } catch (NumberFormatException e) {
                    throw new MapFileParseException("nonsensical line " + line);
                }
            } else {
                // now we have map data
                String[] columns = line.split(" ");
                for (int x = 0; x < tileMap.getColumns(); x++) {
                    if (x >= columns.length)
                        throw new IllegalArgumentException("not enough tiles in row " + y + " (line " + (i + 1) + ")");

                    try {
                        // may have properties: id,prop1,prop2
                        String[] tileData = columns[x].split(",");
                        int tileId = Integer.parseInt(tileData[0]);
                        try {
                            Tile tile = tileSet.get(tileId);
                            // properties are every string after tileId
                            String[] properties = Arrays.copyOfRange(tileData, 1, tileData.length);
                            tileMap.set(x, y, tile, properties);
                        } catch (IndexOutOfBoundsException e) {
                            // will be thrown if tileSet.get(tileId) is out of range
                            throw new MapFileParseException("invalid tile ID (" + columns[x] + ")");
                        }
                    } catch (NumberFormatException e) {
                        throw new MapFileParseException("invalid tile ID (" + columns[x] + ")");
                    }
                }

                y++;
            }
        }

        // if tileMap is STILL null, it means there was never a line which specified rows,cols,tileSize
        if (tileMap == null)
            throw new MapFileParseException("map file does not have a specified rows,cols,tileSize");

        return tileMap;
    }
}
