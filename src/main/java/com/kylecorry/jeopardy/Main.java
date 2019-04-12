package com.kylecorry.jeopardy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Started game");
        String port = System.getProperty("server.port");
        if (port == null) {
            port(80);
        } else {
            port(Integer.valueOf(port));
        }

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
