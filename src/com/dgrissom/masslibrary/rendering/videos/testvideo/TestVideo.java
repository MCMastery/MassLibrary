package com.dgrissom.masslibrary.rendering.videos.testvideo;

import com.dgrissom.masslibrary.rendering.videos.Video;
import com.dgrissom.masslibrary.rendering.videos.VideoRenderer;

import java.io.File;
import java.io.IOException;

public final class TestVideo {
    private TestVideo() {}

    public static void main(String[] args) throws IOException {
        Video video = new Video(new File("renderings/videos/testing"), 120, 30, 800, 600);
        VideoRenderer renderer = new VideoRenderer(video);
        renderer.getStage().add(new TestActor());
        renderer.render();
    }
}
