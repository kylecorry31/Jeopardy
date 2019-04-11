package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.JeopardyQuestion;

public class JeopardyQuestionFactory {
    private JeopardyQuestionFactory(){}

    /**
     * Construct a Jeopardy question
     * @param question the question
     * @param answer the answer
     * @return the JeopardyQuestion
     */
    public static JeopardyQuestion makeQuestion(String question, String answer){
        return new JeopardyQuestionImpl(question, answer);
    }
}
