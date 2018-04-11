package com.dgrissom.masslibrary;

import javafx.scene.input.KeyCharacterCombination;
import javafx.stage.Stage;

public final class FXUtils {
    private FXUtils() {}

    public static void fullscreen(Stage stage) {
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(new KeyCharacterCombination(""));
        stage.setFullScreen(true);
    }
}
