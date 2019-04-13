package com.kylecorry.jeopardy.game.buzzer;

public class MockBuzzer implements Buzzer {

    private BuzzerState state;

    public MockBuzzer(){
        state = BuzzerState.DISCONNECTED;
    }

    @Override
    public void setState(BuzzerState state) {
        this.state = state;
    }

    @Override
    public BuzzerState getState() {
        return state;
    }
}
