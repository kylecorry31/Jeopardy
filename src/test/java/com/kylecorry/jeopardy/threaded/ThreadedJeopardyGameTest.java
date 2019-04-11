package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static com.kylecorry.jeopardy.threaded.JeopardyQuestionFactory.*;
import static com.kylecorry.jeopardy.threaded.JeopardyCategoryFactory.*;
import static com.kylecorry.jeopardy.threaded.PlayerFactory.*;

public class ThreadedJeopardyGameTest {

    private JeopardyBoard board;
    private JeopardyGame game;

    @Before
    public void setup(){
        Map<JeopardyCategory, List<JeopardyQuestion>> questions = new HashMap<>();
        questions.put(makeCategory("C1"), Arrays.asList(
                makeQuestion("Q1", "A1"),
                makeQuestion("Q2", "A2")
        ));
        board = new JeopardyBoardImpl(questions);
        game = new ThreadedJeopardyGame(board);
    }


    @Test
    public void canSelectQuestion(){
        game.selectQuestion(makeCategory("C1"), 100);
        assertEquals(makeQuestion("Q1", "A1"), board.getSelectedQuestion());
    }

    @Test
    public void canDeselectQuestion(){
        game.selectQuestion(makeCategory("C1"), 100);
        game.deselectQuestion();
        assertNull(board.getSelectedQuestion());
    }

    @Test
    public void playerBuzzesInEarly(){
        Player player = makePlayer("P1");
        assertEquals(BuzzResult.INVALID, game.buzz(player, () -> false));
    }

    @Test
    public void playerGetsAnswerCorrect(){
        Player player = makePlayer("P1");
        game.selectQuestion(makeCategory("C1"), 100);
        assertEquals(BuzzResult.CORRECT, game.buzz(player, () -> true));
        assertEquals(100, player.getScore());
    }

    @Test
    public void playerGetsAnswerIncorrect(){
        Player player = makePlayer("P1");
        game.selectQuestion(makeCategory("C1"), 100);
        assertEquals(BuzzResult.INCORRECT, game.buzz(player, () -> false));
        assertEquals(-100, player.getScore());
    }

}