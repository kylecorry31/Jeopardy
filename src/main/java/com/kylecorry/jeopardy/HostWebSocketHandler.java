package com.kylecorry.jeopardy;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class HostWebSocketHandler {

    private JeopardyGame game;

    public HostWebSocketHandler(JeopardyGame game) {
        this.game = game;
    }

    @OnWebSocketConnect
    public void onConnect(Session user) {
        System.out.println("Admin connected");
        game.addHost(user);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Admin disconnected");
        game.removeHost(user);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        if (message.equalsIgnoreCase("lock")){
            System.out.println("Buzzers locked");
            game.onLockBuzzers();
        } else if (message.equalsIgnoreCase("unlock")){
            System.out.println("Buzzers unlocked");
            game.onUnlockBuzzers();
        }
    }
}
