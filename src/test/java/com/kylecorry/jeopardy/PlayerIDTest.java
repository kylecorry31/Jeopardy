package com.kylecorry.jeopardy;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerIDTest {

    @Test
    public void generatesUniqueIDs(){
        PlayerID p1 = PlayerID.createUniqueID();
        PlayerID p2 = PlayerID.createUniqueID();
        assertNotEquals(p1, p2);
    }

}