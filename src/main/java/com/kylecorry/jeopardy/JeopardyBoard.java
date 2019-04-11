package com.kylecorry.jeopardy;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A Jeopardy board interface
 */
public interface JeopardyBoard {

    /**
     * Select a question on the board
     * @param category the question category
     * @param value the value of the question
     */
    void selectQuestion(JeopardyCategory category, int value);

    /**
     * Deselect the currently selected question and mark it as viewed
     */
    void deselectQuestion();

    /**
     * @return the selected question, or null if no question is selected
     */
    JeopardyQuestion getSelectedQuestion();

    /**
     * @param question the question
     * @return the value of the question
     */
    int getValue(JeopardyQuestion question);

    /**
     * @return the categories
     */
    List<JeopardyCategory> getCategories();

    /**
     * @return the questions on the board which have not yet been selected
     */
    Map<JeopardyCategory, Collection<Integer>> getRemainingQuestions();
}
