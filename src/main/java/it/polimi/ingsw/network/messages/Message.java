package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

/**
 * Abstract concept of Objects to be exchanged between clients and server.
 * To be extended by specific messages.
 */
public abstract class Message {

    private PossibleMessages messageType;

    public PossibleMessages getMessageType() {
        return messageType;
    }

    public void setMessageType(PossibleMessages messageType) {
        this.messageType = messageType;
    }
}
