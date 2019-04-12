package com.kylecorry.jeopardy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Started game");
        port(80);
        staticFileLocation("public/");
        webSocket("/play", PlayerWebSocketHandler.class);
        webSocket("/admin", AdminWebSocketHandler.class);
        init();
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            System.out.println("Running publicly at: " + ip);
            System.out.println("Running locally at "+ InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
