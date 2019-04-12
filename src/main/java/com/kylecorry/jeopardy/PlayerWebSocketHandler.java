package com.kylecorry.jeopardy;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@WebSocket
public class PlayerWebSocketHandler {

    public static boolean buzzedIn = false;
    public static boolean locked = true;

    private static Timer currentTimer;
    private static List<Session> players = new LinkedList<>();

    @OnWebSocketConnect
    public void onConnect(Session user) {
        System.out.println("Buzzer connected");
        players.add(user);
        try {
            if (locked || buzzedIn){
                user.getRemote().sendString("locked");
            } else {
                user.getRemote().sendString("unlocked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("Buzzer disconnected");
        players.remove(user);
        try {
            user.getRemote().sendString("disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void lock(){
        locked = true;
        if (currentTimer != null){
            currentTimer.cancel();
            currentTimer = null;
        }
        buzzedIn = false;
        for(Session player: players){
            try {
                player.getRemote().sendString("locked");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void unlock(){
        locked = false;
        if (currentTimer != null){
            currentTimer.cancel();
            currentTimer = null;
        }
        for(Session player: players){
            try {
                player.getRemote().sendString("unlocked");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void buzz(Session user){
        if (locked){
            return;
        }
        buzzedIn = true;
        AdminWebSocketHandler.notifyAdmins("buzz");
        for(Session player: players){
            try {
                player.getRemote().sendString("locked");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            user.getRemote().sendString("buzz");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unbuzz(Session user){
        if (locked){
            return;
        }
        buzzedIn = false;
        AdminWebSocketHandler.notifyAdmins("unbuzz");
        for(Session player: players){
            try {
                player.getRemote().sendString("unbuzz");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            user.getRemote().sendString("unbuzz");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        if (message.equalsIgnoreCase("buzz") && !buzzedIn && !locked){
            System.out.println("Someone buzzed in");
            buzz(user);
            if (currentTimer != null){
                currentTimer.cancel();
                currentTimer = null;
            }
            currentTimer = new Timer();
            currentTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    buzzedIn = false;
                    if (currentTimer != null){
                        currentTimer.cancel();
                        currentTimer = null;
                    }
                    System.out.println("Times up");
                    unbuzz(user);
                }
            }, 10000);
        }
    }
}
