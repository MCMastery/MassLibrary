package com.dgrissom.masslibrary.games.engine;

import com.dgrissom.masslibrary.rendering.Canvas;

public interface GameObject {
    int getLayer();
    void update();
    void render(Canvas canvas);
}
