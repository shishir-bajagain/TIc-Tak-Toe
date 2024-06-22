package samplefx.tictactoe2;

import javafx.scene.control.Button;

public class Buttons {
    private Button button;
    private boolean lock;

    public Buttons(Button button) {
        this.button = button;
        this.lock = false;
    }

    public void setLock() {
        this.lock = true;
    }

    public void setUnlock() {
        this.lock = false;
    }

    public boolean checkLock() {
        return lock;
    }

    public Button getButton() {
        return button;
    }
}