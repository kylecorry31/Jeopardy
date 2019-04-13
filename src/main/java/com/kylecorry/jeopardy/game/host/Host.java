package com.kylecorry.jeopardy.game.host;

/**
 * The host of a game
 */
public interface Host {

    /**
     * @param state the host's state
     */
    void setState(HostState state);

    /**
     * @return the host's state
     */
    HostState getState();

}
