package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.*;

public class ThreadedJeopardyGame implements JeopardyGame {

    private JeopardyBoard board;
    private volatile boolean buzzedIn;


    public ThreadedJeopardyGame(JeopardyBoard board){
        this.board = board;
    }


    @Override
    public synchronized BuzzResult buzz(Player player, AnswerEvaluator evaluator) {
        if (buzzedIn){
            return BuzzResult.INVALID;
        }
        buzzedIn = true;
        if (board.getSelectedQuestion() == null){
            buzzedIn = false;
            return BuzzResult.INVALID;
        }

        if (evaluator.isCorrect()){
            player.setScore(player.getScore() + board.getValue(board.getSelectedQuestion()));
            buzzedIn = false;
            return BuzzResult.CORRECT;
        } else {
            player.setScore(player.getScore() - board.getValue(board.getSelectedQuestion()));
            buzzedIn = false;
            return BuzzResult.INCORRECT;
        }
    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public void selectQuestion(JeopardyCategory category, int value) {
        board.selectQuestion(category, value);
    }

    @Override
    public void deselectQuestion() {
        board.deselectQuestion();
    }
}
