package com.kylecorry.jeopardy.sockethandlers;

import com.kylecorry.jeopardy.game.JeopardyGame;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import static com.kylecorry.jeopardy.game.host.HostFactory.*;

@WebSocket
public class HostWebSocketHandler {

    private JeopardyGame game;

    public HostWebSocketHandler(JeopardyGame game) {
        this.game = game;
    }

    @OnWebSocketConnect
    public void onConnect(Session user) {
        System.out.println("Host connected");
        game.addHost(makeWebSocketHost(user));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Host disconnected");
        game.removeHost(makeWebSocketHost(user));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        if (message.equalsIgnoreCase("lock")){
            System.out.println("Buzzers locked");
            game.lockBuzzers();
        } else if (message.equalsIgnoreCase("unlock")){
            System.out.println("Buzzers unlocked");
            game.unlockBuzzers();
        }
    }
}
