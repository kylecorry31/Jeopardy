package com.kylecorry.jeopardy;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@WebSocket
public class AdminWebSocketHandler {

    public static List<Session> admins = new LinkedList<>();


    @OnWebSocketConnect
    public void onConnect(Session user) {
        System.out.println("Admin connected");
        admins.add(user);
        try {
            if (PlayerWebSocketHandler.locked){
                user.getRemote().sendString("locked");
            } else if (PlayerWebSocketHandler.buzzedIn){
                user.getRemote().sendString("buzz");
            } else {
                user.getRemote().sendString("unlocked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Admin disconnected");
        admins.remove(user);
        try {
            user.getRemote().sendString("disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void notifyAdmins(String message){
        for(Session admin: admins){
            try {
                admin.getRemote().sendString(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        if (message.equalsIgnoreCase("lock")){
            System.out.println("Buzzers locked");
            PlayerWebSocketHandler.lock();
        } else if (message.equalsIgnoreCase("unlock")){
            System.out.println("Buzzers unlocked");
            PlayerWebSocketHandler.unlock();
        }
    }
}
