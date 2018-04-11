package com.dgrissom.masslibrary.games.input;

import javafx.scene.input.KeyCode;

public interface KeyListener {
    void onKeyPress(KeyCode key);
    void onKeyRelease(KeyCode key);
}
