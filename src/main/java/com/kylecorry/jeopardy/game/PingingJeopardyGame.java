package com.kylecorry.jeopardy.game;

import com.kylecorry.jeopardy.game.buzzer.Buzzer;
import com.kylecorry.jeopardy.game.host.Host;

import java.util.Timer;
import java.util.TimerTask;

public class PingingJeopardyGame extends JeopardyGame {
    /**
     * Default constructor
     *
     * @param answerTime the time to answer in seconds
     * @param pingTime the frequency of pings in seconds
     */
    public PingingJeopardyGame(long answerTime, long pingTime) {
        super(answerTime);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(Buzzer buzzer: buzzers){
                    buzzer.getState();
                }

                for(Host host: hosts){
                    host.getState();
                }
            }
        }, 0, pingTime * 1000);
    }
}
