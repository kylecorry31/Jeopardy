package com.kylecorry.jeopardy;

import com.kylecorry.jeopardy.game.JeopardyGame;
import com.kylecorry.jeopardy.game.PingingJeopardyGame;
import com.kylecorry.jeopardy.sockethandlers.BuzzerWebSocketHandler;
import com.kylecorry.jeopardy.sockethandlers.HostWebSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import static spark.Spark.*;

public class Main {

    private static final long ANSWER_TIME = 6;
    private static final long PING_TIME = 2;
    private static final int DEFAULT_PORT = 8080;
    private static final String HOST_ENDPOINT = "/admin";
    private static final String BUZZER_ENDPOINT = "/play";
    private static final String STATIC_WEB_FOLDER = "public/";

    public static void main(String[] args) {
        String port = System.getProperty("server.port");
        if (port == null) {
            port(DEFAULT_PORT);
        } else {
            port(Integer.valueOf(port));
        }

        staticFileLocation(STATIC_WEB_FOLDER);
        JeopardyGame game = new PingingJeopardyGame(ANSWER_TIME, PING_TIME);
        BuzzerWebSocketHandler buzzerWebSocketHandler = new BuzzerWebSocketHandler(game);
        HostWebSocketHandler hostWebSocketHandler = new HostWebSocketHandler(game);
        webSocket(BUZZER_ENDPOINT, buzzerWebSocketHandler);
        webSocket(HOST_ENDPOINT, hostWebSocketHandler);
        init();
        System.out.println("Started game");
        System.out.println("Running publicly at: " + getPublicIPAddress());
        System.out.println("Running locally at "+ getLocalIPAddress());
    }


    private static String getPublicIPAddress(){
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getLocalIPAddress(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }
}
