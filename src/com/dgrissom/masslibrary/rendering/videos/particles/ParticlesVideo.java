package com.dgrissom.masslibrary.rendering.videos.particles;

import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.videos.Video;
import com.dgrissom.masslibrary.rendering.videos.VideoRenderer;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ParticlesVideo {
    private static Set<Particle> particles = new HashSet<>();

    private ParticlesVideo() {}

    public static void main(String[] args) throws IOException {
        Video video = new Video(new File("renderings/videos/particles"), 1000, 60, 1366, 768);
        VideoRenderer vr = new VideoRenderer(video);
        Rectangle2d bounds = video.bounds();
        for (int i = 0; i < 10000; i++) {
            Particle particle = Particle.random(bounds);
            particles.add(particle);
            vr.getStage().add(particle);
        }
        vr.render();
    }

    public static Set<Particle> getParticles() {
        return particles;
    }
}
