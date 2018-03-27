package com.dgrissom.masslibrary.rendering.videos;

import java.util.*;

public class Stage implements Iterable<Actor> {
    private final List<Actor> actors;

    public Stage() {
        this.actors = new ArrayList<>();
    }

    public List<Actor> getActors() {
        return this.actors;
    }
    public void add(Actor... actors) {
        this.actors.addAll(Arrays.asList(actors));
        sortActors();
    }

    public void sortActors() {
        this.actors.sort(Comparator.comparingInt(Actor::getLayer));
    }

    @Override
    public Iterator<Actor> iterator() {
        return this.actors.iterator();
    }
}
