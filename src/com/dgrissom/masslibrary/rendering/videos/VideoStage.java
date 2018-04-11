package com.dgrissom.masslibrary.rendering.videos;

import java.util.*;

public class VideoStage implements Iterable<VideoActor> {
    private final List<VideoActor> actors;

    public VideoStage() {
        this.actors = new ArrayList<>();
    }

    public List<VideoActor> getActors() {
        return this.actors;
    }
    public void add(VideoActor... actors) {
        this.actors.addAll(Arrays.asList(actors));
        sortActors();
    }

    public void sortActors() {
        this.actors.sort(Comparator.comparingInt(VideoActor::getLayer));
    }

    @Override
    public Iterator<VideoActor> iterator() {
        return this.actors.iterator();
    }
}
