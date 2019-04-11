package com.kylecorry.jeopardy;

/**
 * An answer evaluator
 */
@FunctionalInterface
public interface AnswerEvaluator {

    /**
     * @return true if the answer was correct, false otherwise
     */
    boolean isCorrect();

}
