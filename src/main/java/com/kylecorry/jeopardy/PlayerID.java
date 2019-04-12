package com.kylecorry.jeopardy;

import java.util.Objects;

/**
 * A unique player ID
 */
public class PlayerID {

    private int id;
    private static int lastID = -1;

    private PlayerID(int id){
        this.id = id;
    }

    /**
     * Create a unique player ID
     * @return a player ID
     */
    public static PlayerID createUniqueID(){
        return new PlayerID(++lastID);
    }

    public static PlayerID createExistingID(int id) {
        return new PlayerID(id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PlayerID))
            return false;
        PlayerID playerID = (PlayerID) o;
        return playerID.id == id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
