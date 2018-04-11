package com.dgrissom.masslibrary.rendering.videos;

import com.dgrissom.masslibrary.rendering.Image;

public interface VideoActor {
    int getLayer();
    void render(Image image);
}
