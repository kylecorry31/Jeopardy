package com.kylecorry.jeopardy;

/**
 * A player
 */
public interface Player {

    /**
     * @return the score
     */
    int getScore();

    /**
     * @param score the score
     */
    void setScore(int score);

    /**
     * @return their name
     */
    String getName();

    /**
     * @return the unique ID of the player
     */
    PlayerID getID();

}
