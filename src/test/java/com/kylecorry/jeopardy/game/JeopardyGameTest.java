package com.kylecorry.jeopardy.game;

import com.kylecorry.jeopardy.game.buzzer.Buzzer;
import com.kylecorry.jeopardy.game.buzzer.MockBuzzer;
import com.kylecorry.jeopardy.game.host.Host;
import com.kylecorry.jeopardy.game.host.HostState;
import com.kylecorry.jeopardy.game.host.MockHost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.kylecorry.jeopardy.game.buzzer.BuzzerState.*;

class JeopardyGameTest {

    private JeopardyGame game;

    @BeforeEach
    void setup(){
        game = new JeopardyGame(60);
    }

    @Test
    void canAddBuzzers(){
        Buzzer buzzer = new MockBuzzer();
        game.addBuzzer(buzzer);
        assertEquals(INACTIVE, buzzer.getState());
    }

    @Test
    void canRemoveBuzzers(){
        Buzzer buzzer = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.removeBuzzer(buzzer);
        assertEquals(DISCONNECTED, buzzer.getState());
    }

    @Test
    void canAddHost(){
        Host host = new MockHost();
        game.addHost(host);
        assertEquals(HostState.LOCKED, host.getState());
    }

    @Test
    void canRemoveHost(){
        Host host = new MockHost();
        game.addHost(host);
        game.removeHost(host);
        assertEquals(HostState.DISCONNECTED, host.getState());
    }

    @Test
    void notifiesInitialState(){
        Buzzer buzzer = new MockBuzzer();
        Host host = new MockHost();
        game.unlockBuzzers();
        game.addBuzzer(buzzer);
        game.addHost(host);
        assertEquals(ACTIVE, buzzer.getState());
        assertEquals(HostState.UNLOCKED, host.getState());
        game.removeHost(host);
        game.buzzIn(buzzer);
        game.addHost(host);
        assertEquals(HostState.PLAYER_BUZZED_IN, host.getState());
    }

    @Test
    void timerWorks(){
        game = new JeopardyGame(0);
        Buzzer buzzer = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.unlockBuzzers();
        game.buzzIn(buzzer);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(ACTIVE, buzzer.getState());
    }

    @Test
    void hostIsNotified(){
        Host host = new MockHost();
        Buzzer buzzer = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.addHost(host);
        game.unlockBuzzers();
        assertEquals(HostState.UNLOCKED, host.getState());
        game.buzzIn(buzzer);
        assertEquals(HostState.PLAYER_BUZZED_IN, host.getState());
        game.lockBuzzers();
        assertEquals(HostState.LOCKED, host.getState());
    }

    @Test
    void unaddedBuzzerCantBuzzIn(){
        Buzzer buzzer = new MockBuzzer();
        Buzzer buzzer2 = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.unlockBuzzers();
        game.buzzIn(buzzer2);
        assertEquals(DISCONNECTED, buzzer2.getState());
        assertEquals(ACTIVE, buzzer.getState());
    }

    @Test
    void canUnlockBuzzers(){
        Buzzer buzzer = new MockBuzzer();
        Buzzer buzzer2 = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.unlockBuzzers();
        assertEquals(ACTIVE, buzzer.getState());
        assertEquals(ACTIVE, buzzer2.getState());
    }

    @Test
    void canLockBuzzers(){
        Buzzer buzzer = new MockBuzzer();
        Buzzer buzzer2 = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.unlockBuzzers();// Unlock them
        game.lockBuzzers(); // Then lock them
        assertEquals(INACTIVE, buzzer.getState());
        assertEquals(INACTIVE, buzzer2.getState());
    }

    @Test
    void canBuzzIn(){
        Buzzer buzzer = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.unlockBuzzers();
        game.buzzIn(buzzer);
        assertEquals(BUZZED_IN, buzzer.getState());
    }

    @Test
    void cantBuzzInWhileLocked(){
        Buzzer buzzer = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.buzzIn(buzzer);
        assertEquals(INACTIVE, buzzer.getState());
    }

    @Test
    void onlyOnePlayerCanBuzzInAtOnce(){
        Buzzer buzzer = new MockBuzzer();
        Buzzer buzzer2 = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.unlockBuzzers();
        game.buzzIn(buzzer);
        assertEquals(BUZZED_IN, buzzer.getState());
        assertEquals(INACTIVE, buzzer2.getState());
    }

    @Test
    void lockingEndsBeingBuzzedIn(){
        Buzzer buzzer = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.unlockBuzzers();
        game.buzzIn(buzzer);
        game.lockBuzzers();
        assertEquals(INACTIVE, buzzer.getState());
    }

    @Test
    void unlocksWhenBuzzerExpired(){
        Buzzer buzzer = new MockBuzzer();
        Buzzer buzzer2 = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.unlockBuzzers();
        game.buzzIn(buzzer);
        game.buzzExpire();
        assertEquals(ACTIVE, buzzer.getState());
        assertEquals(ACTIVE, buzzer2.getState());
    }

    @Test
    void doesNothingWhenLockedAndBuzzExpires(){
        Buzzer buzzer = new MockBuzzer();
        Buzzer buzzer2 = new MockBuzzer();
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.unlockBuzzers();
        game.buzzIn(buzzer);
        game.lockBuzzers();
        game.buzzExpire();
        assertEquals(INACTIVE, buzzer.getState());
        assertEquals(INACTIVE, buzzer2.getState());
    }

}