package com.dgrissom.masslibrary.rendering.videos.bouncingball;

import com.dgrissom.masslibrary.physics.PhysicsSystem2d;
import com.dgrissom.masslibrary.rendering.videos.Video;
import com.dgrissom.masslibrary.rendering.videos.VideoRenderer;

import java.io.File;
import java.io.IOException;

public final class BouncingBallVideo {
    public static final double FLOOR_Y = 700;
    public static final PhysicsSystem2d SYSTEM = new PhysicsSystem2d();

    private BouncingBallVideo() {}

    public static void main(String[] args) throws IOException {
        Video video = new Video(new File("renderings/videos/bouncingball"), 900, 60, 1366, 768);
        VideoRenderer vr = new VideoRenderer(video);
        vr.getStage().add(new BouncingBall(), new Floor());
        vr.render();
    }
}
