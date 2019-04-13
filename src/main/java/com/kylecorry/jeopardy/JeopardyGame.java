package com.kylecorry.jeopardy;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.*;

public class JeopardyGame {

    private static final long MILLIS_PER_SECOND = 1000;
    private static final long ANSWER_TIME = 6 * MILLIS_PER_SECOND;
    private static final String MESSAGE_LOCKED = "locked";
    private static final String MESSAGE_UNLOCKED = "unlocked";
    private static final String MESSAGE_BUZZ = "buzz";
    private static final String MESSAGE_BUZZ_EXPIRE = "unbuzz";
    private static final String MESSAGE_DISCONNECTED = "disconnected";

    private List<Session> hosts;
    private List<Session> buzzers;
    private GameState gameState;
    private static Timer buzzExpirationTimer;


    public JeopardyGame(){
        hosts = new LinkedList<>();
        buzzers = new LinkedList<>();
        gameState = new GameState();
    }

    /**
     * Adds a host to the game
     * @param host the host session
     */
    public void addHost(Session host){
        hosts.add(host);
        if (gameState.isLocked()){
            send(host, MESSAGE_LOCKED);
        } else if (gameState.isBuzzedIn()){
            send(host, MESSAGE_BUZZ);
        } else {
            send(host, MESSAGE_UNLOCKED);
        }
    }

    /**
     * Removes a host from the game
     * @param host the host session
     */
    public void removeHost(Session host){
        hosts.remove(host);
        send(host, MESSAGE_DISCONNECTED);
    }

    /**
     * Adds a buzzer to the game
     * @param buzzer the buzzer session
     */
    public void addBuzzer(Session buzzer){
        buzzers.add(buzzer);
        if (gameState.isLocked() || gameState.isBuzzedIn()){
            send(buzzer, MESSAGE_LOCKED);
        } else {
            send(buzzer, MESSAGE_UNLOCKED);
        }
    }

    /**
     * Removes a buzzer to the game
     * @param buzzer the buzzer session
     */
    public void removeBuzzer(Session buzzer){
        buzzers.remove(buzzer);
        send(buzzer, MESSAGE_DISCONNECTED);
    }

    /**
     * Handles when a buzzers buzzes in
     * @param buzzer the buzzer session
     */
    public void onBuzzIn(Session buzzer){
        if (gameState.isLocked() || gameState.isBuzzedIn()){
            return;
        }
        gameState.setBuzzedIn(true);
        if (buzzExpirationTimer != null){
            buzzExpirationTimer.cancel();
            buzzExpirationTimer = null;
        }
        sendToHosts(MESSAGE_BUZZ);
        sendToBuzzers(MESSAGE_LOCKED);
        send(buzzer, MESSAGE_BUZZ);
        buzzExpirationTimer = new Timer();
        buzzExpirationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onBuzzExpired(buzzer);
            }
        }, ANSWER_TIME);
    }

    /**
     * Handles when a buzzer's buzz expires
     * @param buzzer the buzzer session
     */
    public void onBuzzExpired(Session buzzer){
        if (gameState.isLocked()){
            return;
        }
        gameState.setBuzzedIn(false);
        if (buzzExpirationTimer != null){
            buzzExpirationTimer.cancel();
            buzzExpirationTimer = null;
        }
        sendToHosts(MESSAGE_BUZZ_EXPIRE);
        sendToBuzzers(MESSAGE_BUZZ_EXPIRE);
    }

    /**
     * Handles when the buzzers become locked
     */
    public void onLockBuzzers(){
        gameState.setLocked(true);
        if (buzzExpirationTimer != null){
            buzzExpirationTimer.cancel();
            buzzExpirationTimer = null;
        }
        gameState.setBuzzedIn(false);
        sendToBuzzers(MESSAGE_LOCKED);
        sendToHosts(MESSAGE_LOCKED);
    }

    /**
     * Handles when the buzzers unlock
     */
    public void onUnlockBuzzers(){
        gameState.setLocked(false);
        if (buzzExpirationTimer != null){
            buzzExpirationTimer.cancel();
            buzzExpirationTimer = null;
        }
        sendToBuzzers(MESSAGE_UNLOCKED);
        sendToHosts(MESSAGE_UNLOCKED);
    }

    /**
     * @return true if buzzed in, false otherwise
     */
    public boolean isBuzzedIn() {
        return gameState.isBuzzedIn();
    }

    /**
     * @return true if locked, false otherwise
     */
    public boolean isLocked() {
        return gameState.isLocked();
    }


    private void send(Session session, String message){
        try {
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToBuzzers(String message){
        for(Session buzzer: buzzers){
            send(buzzer, message);
        }
    }

    private void sendToHosts(String message){
        for(Session host: hosts){
            send(host, message);
        }
    }

}
