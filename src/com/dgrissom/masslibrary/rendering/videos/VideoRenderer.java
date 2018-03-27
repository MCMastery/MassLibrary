package com.dgrissom.masslibrary.rendering.videos;

import com.dgrissom.masslibrary.StringUtils;
import com.dgrissom.masslibrary.files.FileUtils;
import com.dgrissom.masslibrary.rendering.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;

public class VideoRenderer {
    private final Video video;
    private Stage stage;

    public VideoRenderer(Video video) {
        this.video = video;
        this.stage = new Stage();
    }

    public Video getVideo() {
        return this.video;
    }
    public Stage getStage() {
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

        for (int frame = 0; frame < this.video.getFrames(); frame++) {
            Image image = new Image(this.video.getWidth(), this.video.getHeight(), false);
            image.antialias(true);
            for (Actor actor : this.stage)
                actor.render(image);

            File frameFile = new File(framesFolder, this.video.frameFileName(frame));
            image.save(frameFile);

            String percent = String.valueOf((int) Math.round(((double) (frame + 1) / this.video.getFrames()) * 100));
            System.out.println("Rendered frame " + (frame + 1) + "/" + this.video.getFrames() + " (" + percent + "%)");
        }

        System.out.println();

        System.out.println("Converting frames to video...");
        System.out.println(this.video.ffmpegCommand());
        this.video.executeFFmpegCommand();

        System.out.println();

        // delete frames
        FileUtils.deleteNonEmptyFolder(framesFolder);

        long timeEnded = System.currentTimeMillis();
        String duration = StringUtils.fromDuration(timeEnded - timeStarted);

        System.out.println("Done, video saved to " + outputFile.getName() + "(" + duration + ")");
    }
}
