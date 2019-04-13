package com.kylecorry.jeopardy;

/**
 * The state of the game
 */
public class GameState {

    private boolean isBuzzedIn;
    private boolean isLocked;

    /**
     * Default constructor
     */
    public GameState() {
        isBuzzedIn = false;
        isLocked = true;
    }

    /**
     * @return true if buzzed in, false otherwise
     */
    public boolean isBuzzedIn() {
        return isBuzzedIn;
    }

    /**
     * @param buzzedIn whether a player is buzzed in
     */
    public void setBuzzedIn(boolean buzzedIn) {
        isBuzzedIn = buzzedIn;
    }

    /**
     * @return true if locked, false otherwise
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * @param locked whether the game is locked
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
