package com.dgrissom.masslibrary.games.testing;

import com.dgrissom.masslibrary.games.engine.GameEngine;

public class TestGame implements Game {
    private static TestGame instance;

    private TestGame() {
        instance = this;
    }

    @Override
    public void init() {
        GameEngine.getInstance().add(new TestObject());
    }

    public static void main(String[] args) {
        GameEngine.start(instance = new TestGame());
    }

    public static TestGame getInstance() {
        return instance;
    }
}
