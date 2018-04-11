package com.dgrissom.masslibrary.games.rpg;

import com.dgrissom.masslibrary.games.Camera;
import com.dgrissom.masslibrary.games.GameActor;
import com.dgrissom.masslibrary.games.input.Input;
import com.dgrissom.masslibrary.games.tilemap.TileMap;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.InvertibleTransform;
import com.dgrissom.masslibrary.math.geom.r2.Circle2d;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.Vector2d;
import com.dgrissom.masslibrary.math.geom.r2.transform.Translate2d;
import com.dgrissom.masslibrary.rendering.Canvas;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import javafx.scene.input.KeyCode;

class Player implements GameActor, Camera {
    private Point2d position;

    public Player() throws TileMap.MapFileParseException {
        this.position = RPGMap.getPlayerSpawn();
        if (this.position == null)
            throw new TileMap.MapFileParseException("no player spawn set");
    }

    private Translate2d translation() {
        return new Translate2d(this.position.multiply(-1));
    }

    @Override
    public InvertibleTransform inverse() {
        return translation().inverse();
    }
    @Override
    public Matrix matrix() {
        return translation().matrix();
    }

    @Override
    public int getLayer() {
        return 0;
    }
    @Override
    public void update() {
        Vector2d movement = new Vector2d(0, 0);
        if (Input.keyDown(KeyCode.W))
            movement = movement.add(0, -1);
        if (Input.keyDown(KeyCode.A))
            movement = movement.add(-1, 0);
        if (Input.keyDown(KeyCode.S))
            movement = movement.add(0, 1);
        if (Input.keyDown(KeyCode.D))
            movement = movement.add(1, 0);
        movement = movement.normalize().multiply(4);
        this.position = this.position.add(movement).round();
    }
    @Override
    public void render(Canvas canvas) {
        canvas.fill(new Circle2d(this.position, 8), RGBColor.WHITE);
    }
}
