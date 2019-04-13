package com.kylecorry.jeopardy.game.buzzer;

import org.eclipse.jetty.websocket.api.Session;

/**
 * A buzzer factory
 */
public class BuzzerFactory {

    private BuzzerFactory(){}

    /**
     * Create a buzzer on a web socket
     * @param session the web socket session
     * @return the buzzer
     */
    public static Buzzer makeWebSocketBuzzer(Session session){
        return new WebSocketBuzzer(session);
    }

}
