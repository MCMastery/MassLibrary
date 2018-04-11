package com.dgrissom.masslibrary.games.tilemap;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.rendering.Image;

import java.io.File;
import java.io.IOException;

public interface Tile {
    void render(int x, int y, Image image);

    static Tile load(File imageFile) throws IOException {
        Image fxImage = Image.load(imageFile);
        return (x, y, image) -> image.draw(fxImage, new Point2d(x * fxImage.getWidth(), y * fxImage.getHeight()));
    }
}
