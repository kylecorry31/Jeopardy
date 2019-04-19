package com.kylecorry.jeopardy.game.buzzer;

import com.kylecorry.jeopardy.game.WebSocketMessages;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Objects;

/**
 * A buzzer which uses a web socket
 */
public class WebSocketBuzzer implements Buzzer {

    private Session session;
    private BuzzerState state;

    /**
     * Default constructor
     * @param session the web socket session
     */
    public WebSocketBuzzer(Session session) {
        this.session = Objects.requireNonNull(session);
        state = BuzzerState.DISCONNECTED;
    }

    @Override
    public void setState(BuzzerState state) {
        this.state = state;
        switch (state){
            case ACTIVE:
                send(WebSocketMessages.BUZZER_ACTIVATED);
                break;
            case INACTIVE:
                send(WebSocketMessages.BUZZER_DEACTIVATED);
                break;
            case BUZZED_IN:
                send(WebSocketMessages.BUZZER_BUZZED_IN);
                break;
        }
    }

    @Override
    public BuzzerState getState() {
        if (state != BuzzerState.DISCONNECTED){
            send(WebSocketMessages.PING);
        }
        return state;
    }

    private void send(String message){
        try {
            if (!session.isOpen()){
                return;
            }
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, session);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof WebSocketBuzzer))
            return false;
        WebSocketBuzzer buzzer = (WebSocketBuzzer) o;
        return buzzer.session.equals(session);
    }

    @Override
    public String toString() {
        return "Web Socket Buzzer (" + state + ")";
    }
}
