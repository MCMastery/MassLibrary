package com.dgrissom.masslibrary.games;

import java.util.*;

public class GameStage implements Iterable<GameActor> {
    private final List<GameActor> actors;
    private Camera camera;

    public GameStage() {
        this.actors = new ArrayList<>();
        this.camera = null;
    }

    public List<GameActor> getActors() {
        return this.actors;
    }
    public void add(GameActor... actors) {
        this.actors.addAll(Arrays.asList(actors));
        sortActors();
    }
    public void remove(GameActor... actors) {
        this.actors.removeAll(Arrays.asList(actors));
    }
    public Camera getCamera() {
        return this.camera;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void sortActors() {
        this.actors.sort(Comparator.comparingInt(GameActor::getLayer));
    }

    @Override
    public Iterator<GameActor> iterator() {
        return this.actors.iterator();
    }
}
