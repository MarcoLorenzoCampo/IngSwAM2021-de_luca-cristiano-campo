package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

/**
 * Message sent by the client to ping the client handler.
 * Ping due more often than every 5 seconds.
 */
public class PingMessage extends Message {

    public PingMessage(String username) {
        super.setMessageType(PossibleMessages.PING_MESSAGE);
        super.setSenderUsername(username);
    }
}
