package com.kylecorry.jeopardy.game;

import com.kylecorry.jeopardy.game.buzzer.Buzzer;
import com.kylecorry.jeopardy.game.buzzer.BuzzerState;
import com.kylecorry.jeopardy.game.host.Host;
import com.kylecorry.jeopardy.game.host.HostState;
import java.util.*;

/**
 * A Jeopardy game
 */
public class JeopardyGame {

    private static final long MILLIS_PER_SECOND = 1000;

    private List<Host> hosts;
    private List<Buzzer> buzzers;
    private GameState gameState;
    private static Timer buzzExpirationTimer;
    private final long answerTime;


    /**
     * Default constructor
     * @param answerTime the time to answer in seconds
     */
    public JeopardyGame(long answerTime){
        hosts = new LinkedList<>();
        buzzers = new LinkedList<>();
        gameState = new GameState();
        this.answerTime = answerTime * MILLIS_PER_SECOND;
    }

    /**
     * Adds a host to the game
     * @param host the host session
     */
    public synchronized void addHost(Host host){
        hosts.add(host);
        if (gameState.isLocked()){
            host.setState(HostState.LOCKED);
        } else if (gameState.isBuzzedIn()){
            host.setState(HostState.PLAYER_BUZZED_IN);
        } else {
            host.setState(HostState.UNLOCKED);
        }
    }

    /**
     * Removes a host from the game
     * @param host the host session
     */
    public synchronized void removeHost(Host host){
        hosts.remove(host);
        host.setState(HostState.DISCONNECTED);
    }

    /**
     * Adds a buzzer to the game
     * @param buzzer the buzzer session
     */
    public synchronized void addBuzzer(Buzzer buzzer){
        buzzers.add(buzzer);
        if (gameState.isLocked() || gameState.isBuzzedIn()){
            buzzer.setState(BuzzerState.INACTIVE);
        } else {
            buzzer.setState(BuzzerState.ACTIVE);
        }
    }

    /**
     * Removes a buzzer to the game
     * @param buzzer the buzzer session
     */
    public synchronized void removeBuzzer(Buzzer buzzer){
        buzzers.remove(buzzer);
        buzzer.setState(BuzzerState.DISCONNECTED);
    }

    /**
     * Handles when a buzzers buzzes in
     * @param buzzer the buzzer session
     */
    public synchronized void buzzIn(Buzzer buzzer){
        if (gameState.isLocked() || gameState.isBuzzedIn() || !buzzers.contains(buzzer)){
            return;
        }
        gameState.setBuzzedIn(true);

        hosts.forEach(h -> h.setState(HostState.PLAYER_BUZZED_IN));
        buzzers.stream().filter(b -> !b.equals(buzzer)).forEach(b -> b.setState(BuzzerState.INACTIVE));
        buzzer.setState(BuzzerState.BUZZED_IN);

        if (buzzExpirationTimer != null){
            buzzExpirationTimer.cancel();
            buzzExpirationTimer = null;
        }
        buzzExpirationTimer = new Timer();
        buzzExpirationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                buzzExpire();
            }
        }, answerTime);
    }

    /**
     * Handles when a buzzer's buzz expires
     */
    public synchronized void buzzExpire(){
        if (gameState.isLocked()){
            return;
        }
        gameState.setBuzzedIn(false);
        if (buzzExpirationTimer != null){
            buzzExpirationTimer.cancel();
            buzzExpirationTimer = null;
        }
        hosts.forEach(h -> h.setState(HostState.UNLOCKED));
        buzzers.forEach(b -> b.setState(BuzzerState.ACTIVE));
    }

    /**
     * Handles when the buzzers become locked
     */
    public void lockBuzzers(){
        gameState.setLocked(true);
        if (buzzExpirationTimer != null){
            buzzExpirationTimer.cancel();
            buzzExpirationTimer = null;
        }
        gameState.setBuzzedIn(false);
        buzzers.forEach(b -> b.setState(BuzzerState.INACTIVE));
        hosts.forEach(h -> h.setState(HostState.LOCKED));
    }

    /**
     * Handles when the buzzers unlock
     */
    public void unlockBuzzers(){
        gameState.setLocked(false);
        if (buzzExpirationTimer != null){
            buzzExpirationTimer.cancel();
            buzzExpirationTimer = null;
        }
        buzzers.forEach(b -> b.setState(BuzzerState.ACTIVE));
        hosts.forEach(h -> h.setState(HostState.UNLOCKED));
    }

}
