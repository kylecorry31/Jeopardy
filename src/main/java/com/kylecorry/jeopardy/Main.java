package com.kylecorry.jeopardy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        String port = System.getProperty("server.port");
        if (port == null) {
            port(8080);
        } else {
            port(Integer.valueOf(port));
        }

        staticFileLocation("public/");
        JeopardyGame game = new JeopardyGame();
        BuzzerWebSocketHandler buzzerWebSocketHandler = new BuzzerWebSocketHandler(game);
        HostWebSocketHandler hostWebSocketHandler = new HostWebSocketHandler(game);
        webSocket("/play", buzzerWebSocketHandler);
        webSocket("/admin", hostWebSocketHandler);
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
