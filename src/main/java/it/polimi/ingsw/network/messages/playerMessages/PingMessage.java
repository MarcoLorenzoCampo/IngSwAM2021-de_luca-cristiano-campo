package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

/**
 * Message sent by the client to ping the client handler.
 * Ping due more often than every 5 seconds.
 */
public class PingMessage extends Message {

    private static final long serialVersionUID = -4046380756090971644L;

    public PingMessage() {
        super.setMessageType(PossibleMessages.PING_MESSAGE);
        super.setSenderUsername("PING_MESSAGE");
    }
}
