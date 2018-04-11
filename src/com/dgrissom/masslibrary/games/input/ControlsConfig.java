package com.dgrissom.masslibrary.games.input;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class ControlsConfig {
    private Map<Control, KeyCode> controls;

    public ControlsConfig() {
        this.controls = new HashMap<>();
    }

    public Map<Control, KeyCode> getControls() {
        return new HashMap<>(this.controls);
    }
    public KeyCode getControl(Control control) {
        return this.controls.get(control);
    }
    public void addControl(Control control) {
        this.controls.put(control, control.getDefaultKey());
    }
    public void setControl(Control control, KeyCode key) {
        this.controls.put(control, key);
    }
    public void removeControl(Control control) {
        this.controls.remove(control);
    }
}
