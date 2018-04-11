package com.dgrissom.masslibrary.rendering.videos;

import com.dgrissom.masslibrary.StringUtils;
import com.dgrissom.masslibrary.files.FileUtils;
import com.dgrissom.masslibrary.rendering.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;

public class VideoRenderer {
    private final Video video;
    private VideoStage stage;

    public VideoRenderer(Video video) {
        this.video = video;
        this.stage = new VideoStage();
    }

    public Video getVideo() {
        return this.video;
    }
    public VideoStage getStage() {
        return this.stage;
    }

    // WARNING: overwrites output.mp4!
    public void render() throws IOException {
        long timeStarted = System.currentTimeMillis();

        // create frames folder if necessary
        File framesFolder = this.video.framesFolder();
        FileUtils.createFolderIfNeeded(framesFolder);

        // if output.mp4 already exists, delete that one
        // (ffmpeg will get hung up)
        File outputFile = this.video.videoOutputFile();
        if (outputFile.exists())
            if (!outputFile.delete())
                throw new FileSystemException("could not delete previous output.mp4 file");

        double fps = 0, fpsSmoothing = 0.9;
        long lastTime = System.currentTimeMillis();

        for (int frame = 0; frame < this.video.getFrames(); frame++) {
            Image image = new Image(this.video.getWidth(), this.video.getHeight(), false);
            for (VideoActor actor : this.stage)
                actor.render(image);

            File frameFile = new File(framesFolder, this.video.frameFileName(frame));
            image.save(frameFile);

            double curFps = 1000.0 / (System.currentTimeMillis() - lastTime);
            // see https://stackoverflow.com/questions/87304/calculating-frames-per-second-in-a-game
            fps = (fps * fpsSmoothing) + (curFps * (1 - fpsSmoothing));

            long estimatedTimeLeft = (this.video.getFrames() - frame) * (long) (1000 / fps);

            String percent = String.valueOf((int) Math.floor(((double) (frame + 1) / this.video.getFrames()) * 100));
            System.out.println("Rendered frame " + (frame + 1) + "/" + this.video.getFrames() + " (" + percent + "%), "
                    + StringUtils.fromDouble(fps, 2) + " fps. " +
                    "Estimated time left: " + StringUtils.fromDuration(estimatedTimeLeft, false));

            lastTime = System.currentTimeMillis();
        }

        System.out.println();

        System.out.println("Converting frames to video...");
        System.out.println(this.video.ffmpegRenderCommand());
        this.video.executeFFmpegCommand();

        System.out.println();

        // delete frames
        FileUtils.deleteNonEmptyFolder(framesFolder);

        long timeEnded = System.currentTimeMillis();
        String duration = StringUtils.fromDuration(timeEnded - timeStarted, false);

        System.out.println("Done, video saved to " + outputFile.getName() + "(" + duration + ")");
    }
}
