package com.kylecorry.jeopardy.game;

public class WebSocketMessages {

    public static final String BUZZER_ACTIVATED = "unlocked";
    public static final String BUZZER_DEACTIVATED = "locked";
    public static final String BUZZER_BUZZED_IN = "buzz";
    public static final String BUZZER_DISCONNECTED = "disconnected";

    public static final String HOST_UNLOCKED = "unlocked";
    public static final String HOST_LOCKED = "locked";
    public static final String HOST_PLAYER_BUZZED_IN = "buzz";
    public static final String HOST_DISCONNECTED = "disconnected";

    private WebSocketMessages(){}
}
