package com.kylecorry.jeopardy.game.host;

public class MockHost implements Host {

    private HostState state;

    public MockHost(){
        state = HostState.DISCONNECTED;
    }

    @Override
    public void setState(HostState state) {
        this.state = state;
    }

    @Override
    public HostState getState() {
        return state;
    }
}
