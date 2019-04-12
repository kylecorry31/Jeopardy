package com.kylecorry.jeopardy;

import com.kylecorry.jeopardy.threaded.PlayerFactory;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;

public class PlayerWebSocketHandler {



    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        Player player = PlayerFactory.makePlayer("Unknown");
//        Player player = PlayerFactory.createNewPlayer();
//        GameInfo.getInstance().addPlayer(user, player);
//        MessageHandler.getInstance().sendFromServer((player.getName() + " joined the game"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
//        Player player = GameInfo.getInstance().getPlayer(user);
//        GameInfo.getInstance().removePlayer(user);
//        MessageHandler.getInstance().sendFromServer((player.getName() + " left the game"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {

        if (message.contains("playerID")){
            int id = 0;
            Player player = PlayerFactory.getExistingPlayer(PlayerID.createExistingID(id));
        }

//        Player player = GameInfo.getInstance().getPlayer(user);
//        if(message.contains("name:") || message.contains("Name:")){
//            player.setName(message.substring(5));
//            MessageHandler.getInstance().sendFromServer(null);
//        }
//        MessageHandler.getInstance().send(player, message);
    }
}
