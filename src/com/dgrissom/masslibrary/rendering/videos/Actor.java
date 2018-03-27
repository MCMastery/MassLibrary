package com.dgrissom.masslibrary.rendering.videos;

import com.dgrissom.masslibrary.rendering.Image;

public interface Actor {
    int getLayer();
    void render(Image image);
}
