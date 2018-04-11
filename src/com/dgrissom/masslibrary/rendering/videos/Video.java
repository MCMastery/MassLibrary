package com.dgrissom.masslibrary.rendering.videos;

import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystemException;

// https://trac.ffmpeg.org/wiki/Slideshow
// frames are named "001.png", "002.png", etc.
public class Video {
    public static final String FFMPEG_PATH = "C:\\ffmpeg\\bin\\ffmpeg.exe";
    public static final String OUTPUT_FILE_NAME = "output.mp4";
    public static final String FRAME_FILE_NAME = "frame{#}.png";
    public static final String FRAMES_FOLDER_NAME = "frames\\";

    // folder of video
    // video file is folder/output.mp4
    // frames are in folder/frames/...
    private final File folder;
    private final int frames, fps, width, height;

    public Video(File folder, int frames, int fps, int width, int height) {
        this.folder = folder;
        this.frames = frames;
        this.fps = fps;
        this.width = width;
        this.height = height;
    }

    public File getFolder() {
        return this.folder;
    }
    public int getFrames() {
        return this.frames;
    }
    public int getFps() {
        return this.fps;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }

    public Rectangle2d bounds() {
        return new Rectangle2d(this.width, this.height);
    }

    public File videoOutputFile() {
        return new File(this.folder, OUTPUT_FILE_NAME);
    }
    public File framesFolder() {
        return new File(this.folder, FRAMES_FOLDER_NAME);
    }
    // number of zeros prefixing the frame file name
    private int frameFileNameZeros() {
        return String.valueOf(this.frames).length() - 1;
    }
    // 0 will return "frame-001.png", "frame-01.png", etc. # of zeros depending on how many we have
    public String frameFileName(int frame) {
        String number = String.valueOf(frame + 1);
        while (number.length() < frameFileNameZeros() + 1)
            number = "0" + number;
        return FRAME_FILE_NAME.replace("{#}", number);
    }

    public void clearFramesFolder() throws FileSystemException {
        File[] files = framesFolder().listFiles();
        if (files == null)
            return;
        for (File file : files)
            if (!file.delete())
                throw new FileSystemException("could not delete file " + file.getName() + " in frames folder");
    }

    // ffmpeg command to generate video
    // run this command from within the video folder
    public String ffmpegRenderCommand() {
         // ffmpeg -framerate 24 -i img%03d.png output.mp4;
        String frameName = FRAME_FILE_NAME.replace("{#}", "%0" + (frameFileNameZeros() + 1) + "d");
        frameName = FRAMES_FOLDER_NAME + frameName;
        frameName = "\"" + this.folder.getAbsolutePath() + "\\" + frameName + "\"";

        String outputFileName = "\"" + this.folder.getAbsolutePath() + "\\" + OUTPUT_FILE_NAME + "\"";
        return FFMPEG_PATH + " -framerate " + this.fps + " -i " + frameName + " -pix_fmt yuv420p " + outputFileName;
    }

    public void executeFFmpegCommand() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", ffmpegRenderCommand());
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = inStreamReader.readLine()) != null)
            System.out.println(line);
    }

    public static int secondsToFrames(double seconds, int fps) {
        return (int) Math.ceil(seconds * fps);
    }
    public static double framesToSeconds(int frames, int fps) {
        return (double) frames / fps;
    }
}
