package com.kylecorry.jeopardy.game;

import com.kylecorry.jeopardy.game.buzzer.Buzzer;
import com.kylecorry.jeopardy.game.buzzer.BuzzerState;
import com.kylecorry.jeopardy.game.host.Host;
import com.kylecorry.jeopardy.game.host.HostState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JeopardyGameTest {

    private JeopardyGame game;

    @BeforeEach
    void setup(){
        game = new JeopardyGame(60);
    }

    @Test
    void canAddBuzzers(){
        Buzzer buzzer = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        verify(buzzer).setState(BuzzerState.INACTIVE);
    }

    @Test
    void canRemoveBuzzers(){
        Buzzer buzzer = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.removeBuzzer(buzzer);
        verify(buzzer).setState(BuzzerState.DISCONNECTED);
    }

    @Test
    void canAddHost(){
        Host host = mock(Host.class);
        game.addHost(host);
        verify(host).setState(HostState.LOCKED);
    }

    @Test
    void canRemoveHost(){
        Host host = mock(Host.class);
        game.addHost(host);
        game.removeHost(host);
        verify(host).setState(HostState.DISCONNECTED);
    }

    @Test
    void notifiesInitialState(){
        Buzzer buzzer = mock(Buzzer.class);
        Host host = mock(Host.class);
        game.onUnlockBuzzers();
        game.addBuzzer(buzzer);
        game.addHost(host);
        verify(buzzer).setState(BuzzerState.ACTIVE);
        verify(host).setState(HostState.UNLOCKED);
        game.removeHost(host);
        game.onBuzzIn(buzzer);
        game.addHost(host);
        verify(host).setState(HostState.PLAYER_BUZZED_IN);
    }

    @Test
    void timerWorks(){
        game = new JeopardyGame(0);
        Buzzer buzzer = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.onUnlockBuzzers();
        game.onBuzzIn(buzzer);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verify(buzzer, times(2)).setState(BuzzerState.ACTIVE);

    }

    @Test
    void hostIsNotified(){
        Host host = mock(Host.class);
        Buzzer buzzer = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.addHost(host);
        game.onUnlockBuzzers();
        verify(host).setState(HostState.UNLOCKED);
        game.onBuzzIn(buzzer);
        verify(host).setState(HostState.PLAYER_BUZZED_IN);
        game.onLockBuzzers();
        verify(host, times(2)).setState(HostState.LOCKED);
    }

    @Test
    void unaddedBuzzerCantBuzzIn(){
        Buzzer buzzer = mock(Buzzer.class);
        Buzzer buzzer2 = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.onUnlockBuzzers();
        game.onBuzzIn(buzzer2);
        verify(buzzer2, never()).setState(BuzzerState.BUZZED_IN);
        verify(buzzer).setState(BuzzerState.ACTIVE);
    }

    @Test
    void canUnlockBuzzers(){
        Buzzer buzzer = mock(Buzzer.class);
        Buzzer buzzer2 = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.onUnlockBuzzers();
        verify(buzzer).setState(BuzzerState.ACTIVE);
        verify(buzzer2).setState(BuzzerState.ACTIVE);
    }

    @Test
    void canLockBuzzers(){
        Buzzer buzzer = mock(Buzzer.class);
        Buzzer buzzer2 = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.onUnlockBuzzers();// Unlock them
        game.onLockBuzzers(); // Then lock them
        verify(buzzer, times(2)).setState(BuzzerState.INACTIVE);
        verify(buzzer2, times(2)).setState(BuzzerState.INACTIVE);
    }

    @Test
    void canBuzzIn(){
        Buzzer buzzer = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.onUnlockBuzzers();
        game.onBuzzIn(buzzer);
        verify(buzzer).setState(BuzzerState.BUZZED_IN);
    }

    @Test
    void cantBuzzInWhileLocked(){
        Buzzer buzzer = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.onBuzzIn(buzzer);
        verify(buzzer).setState(BuzzerState.INACTIVE);
    }

    @Test
    void onlyOnePlayerCanBuzzInAtOnce(){
        Buzzer buzzer = mock(Buzzer.class);
        Buzzer buzzer2 = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.onUnlockBuzzers();
        game.onBuzzIn(buzzer);
        verify(buzzer).setState(BuzzerState.BUZZED_IN);
        verify(buzzer2, times(2)).setState(BuzzerState.INACTIVE);
    }

    @Test
    void lockingEndsBeingBuzzedIn(){
        Buzzer buzzer = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.onUnlockBuzzers();
        game.onBuzzIn(buzzer);
        game.onLockBuzzers();
        verify(buzzer, times(2)).setState(BuzzerState.INACTIVE);
    }

    @Test
    void unlocksWhenBuzzerExpired(){
        Buzzer buzzer = mock(Buzzer.class);
        Buzzer buzzer2 = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.onUnlockBuzzers();
        game.onBuzzIn(buzzer);
        game.onBuzzExpired();
        verify(buzzer, times(2)).setState(BuzzerState.ACTIVE);
        verify(buzzer2, times(2)).setState(BuzzerState.ACTIVE);
    }

    @Test
    void doesNothingWhenLockedAndBuzzExpires(){
        Buzzer buzzer = mock(Buzzer.class);
        Buzzer buzzer2 = mock(Buzzer.class);
        game.addBuzzer(buzzer);
        game.addBuzzer(buzzer2);
        game.onUnlockBuzzers();
        game.onBuzzIn(buzzer);
        game.onLockBuzzers();
        game.onBuzzExpired();
        verify(buzzer, times(2)).setState(BuzzerState.INACTIVE);
        verify(buzzer2, times(3)).setState(BuzzerState.INACTIVE);
    }

}