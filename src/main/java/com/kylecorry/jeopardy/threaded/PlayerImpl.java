package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.Player;
import com.kylecorry.jeopardy.PlayerID;

import java.util.Objects;

public class PlayerImpl implements Player {

    private int score;
    private String name;
    private PlayerID id;

    /**
     * Default constructor
     * @param name the player's name
     * @param id the player's ID
     */
    public PlayerImpl(String name, PlayerID id) {
        this.name = name;
        this.id = id;
        score = 0;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerID getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerImpl player = (PlayerImpl) o;
        return score == player.score &&
                Objects.equals(name, player.name) &&
                Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, name, id);
    }
}
