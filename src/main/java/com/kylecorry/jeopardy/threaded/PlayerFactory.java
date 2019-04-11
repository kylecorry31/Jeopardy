package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.Player;
import com.kylecorry.jeopardy.PlayerID;

public class PlayerFactory {

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
        return new PlayerImpl(name, new PlayerID());
    }

}
