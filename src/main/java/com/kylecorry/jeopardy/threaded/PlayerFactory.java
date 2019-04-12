package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.Player;
import com.kylecorry.jeopardy.PlayerID;

import java.util.LinkedList;
import java.util.List;

public class PlayerFactory {

    private static List<Player> players = new LinkedList<>();

    private PlayerFactory(){}

    /**
     * Make a player
     * @param name the player's name
     * @param id the player's ID
     * @return the player
     */
    public static Player makePlayer(String name, PlayerID id){
        return new PlayerImpl(name, id);
    }

    /**
     * Make a player
     * @param name the player's name
     * @return the player
     */
    public static Player makePlayer(String name){
        Player player = new PlayerImpl(name, PlayerID.createUniqueID());
        players.add(player);
        return player;
    }

    /**
     * Get an existing player
     * @param playerID the ID of the player
     * @return the player or null if the player does not exist
     */
    public static Player getExistingPlayer(PlayerID playerID){
        for (Player player: players){
            if (player.getID().equals(playerID)){
                return player;
            }
        }
        return null;
    }



}
