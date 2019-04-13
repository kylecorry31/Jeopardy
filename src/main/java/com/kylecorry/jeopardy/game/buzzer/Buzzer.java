package com.kylecorry.jeopardy.game.buzzer;

/**
 * A buzzer
 */
public interface Buzzer {

    /**
     * @param state the state of the buzzer
     */
    void setState(BuzzerState state);

    /**
     * @return the state of the buzzer
     */
    BuzzerState getState();

}
