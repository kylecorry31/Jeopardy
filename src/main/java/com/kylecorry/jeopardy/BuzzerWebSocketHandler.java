package com.kylecorry.jeopardy;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;

@WebSocket
public class BuzzerWebSocketHandler {

    private JeopardyGame game;

    public BuzzerWebSocketHandler(JeopardyGame game) {
        this.game = game;
    }

    @OnWebSocketConnect
    public void onConnect(Session user) {
        System.out.println("Buzzer connected");
        game.addBuzzer(user);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Buzzer disconnected");
        game.removeBuzzer(user);
    }


    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        if (message.equalsIgnoreCase("buzz")){
            System.out.println("Someone buzzed in");
            game.onBuzzIn(user);
        }
    }
}
