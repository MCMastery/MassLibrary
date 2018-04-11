package com.dgrissom.masslibrary.games;

import com.dgrissom.masslibrary.rendering.Canvas;

public interface GameActor {
    int getLayer();
    void update();
    void render(Canvas canvas);
}
