package com.kylecorry.jeopardy.sockethandlers;

import com.kylecorry.jeopardy.game.JeopardyGame;
import com.kylecorry.jeopardy.game.WebSocketMessages;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;

import static com.kylecorry.jeopardy.game.buzzer.BuzzerFactory.*;

@WebSocket
public class BuzzerWebSocketHandler {

    private JeopardyGame game;

    public BuzzerWebSocketHandler(JeopardyGame game) {
        this.game = game;
    }

    @OnWebSocketConnect
    public void onConnect(Session user) {
        System.out.println("Buzzer connected");
        game.addBuzzer(makeWebSocketBuzzer(user));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Buzzer disconnected");
        game.removeBuzzer(makeWebSocketBuzzer(user));
    }


    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        if (message.equalsIgnoreCase(WebSocketMessages.CLIENT_REQUEST_BUZZ)){
            System.out.println("Someone buzzed in");
            game.buzzIn(makeWebSocketBuzzer(user));
        }
    }
}
