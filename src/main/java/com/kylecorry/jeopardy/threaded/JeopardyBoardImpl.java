package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.JeopardyBoard;
import com.kylecorry.jeopardy.JeopardyCategory;
import com.kylecorry.jeopardy.JeopardyQuestion;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JeopardyBoardImpl implements JeopardyBoard {

    private Map<JeopardyCategory, List<JeopardyQuestion>> questions;
    private List<JeopardyQuestion> answered;
    private JeopardyQuestion selected;


    /**
     * Default constructor
     * @param questions the questions
     */
    public JeopardyBoardImpl(Map<JeopardyCategory, List<JeopardyQuestion>> questions) {
        this.questions = questions;
        answered = new LinkedList<>();
        selected = null;
    }

    @Override
    public void selectQuestion(JeopardyCategory category, int value) {
        selected = questions.get(category).get(value / 100 - 1);
    }

    @Override
    public void deselectQuestion() {
        if (selected == null)
            return;
        answered.add(selected);
        selected = null;
    }

    @Override
    public JeopardyQuestion getSelectedQuestion() {
        return selected;
    }

    @Override
    public int getValue(JeopardyQuestion question) {
        for (List<JeopardyQuestion> questionList: questions.values()){
            int index = questionList.indexOf(question);
            if (index != -1){
                return (index + 1) * 100;
            }
        }
        return 0;
    }

    @Override
    public List<JeopardyCategory> getCategories() {
        return new LinkedList<>(questions.keySet());
    }

    @Override
    public Map<JeopardyCategory, Collection<Integer>> getRemainingQuestions() {
        return null;
    }
}
