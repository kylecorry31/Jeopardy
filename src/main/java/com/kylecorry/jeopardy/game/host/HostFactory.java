package com.kylecorry.jeopardy.game.host;

import org.eclipse.jetty.websocket.api.Session;

/**
 * A host factory
 */
public class HostFactory {
    private HostFactory(){}

    /**
     * Create a host on a web socket
     * @param session the web socket session
     * @return the host
     */
    public static Host makeWebSocketHost(Session session){
        return new WebSocketHost(session);
    }
}
