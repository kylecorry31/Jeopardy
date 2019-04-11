package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.JeopardyQuestion;

import java.util.Objects;

public class JeopardyQuestionImpl implements JeopardyQuestion {

    private String question, answer;

    /**
     * Default constructor
     * @param question the question
     * @param answer the answer
     */
    public JeopardyQuestionImpl(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JeopardyQuestionImpl that = (JeopardyQuestionImpl) o;
        return Objects.equals(question, that.question) &&
                Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }
}
