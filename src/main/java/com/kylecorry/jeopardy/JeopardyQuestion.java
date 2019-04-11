package com.kylecorry.jeopardy;

/**
 * A Jeopardy question
 */
public interface JeopardyQuestion {

    /**
     * @return the question (asked to the players)
     */
    String getQuestion();

    /**
     * @return the answer (spoken by the players)
     */
    String getAnswer();
}
