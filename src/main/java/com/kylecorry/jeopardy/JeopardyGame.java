package com.kylecorry.jeopardy;

/**
 * A Jeopardy game
 */
public interface JeopardyGame {

    /**
     * Handle a player buzzing in to answer a question
     * @param player the player who buzzed in
     * @param evaluator the answer evaluator
     * @return the result of the buzz
     */
    BuzzResult buzz(Player player, AnswerEvaluator evaluator);


    /**
     * @return the winning player
     */
    Player getWinner();

    /**
     * Select a question
     * @param category the category
     * @param value the value
     */
    void selectQuestion(JeopardyCategory category, int value);

    /**
     * Deselect the currently selected question
     */
    void deselectQuestion();

}
