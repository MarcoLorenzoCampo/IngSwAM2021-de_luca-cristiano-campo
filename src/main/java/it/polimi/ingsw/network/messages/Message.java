package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

/**
 * Abstract concept of Objects to be exchanged between clients and server.
 * To be extended by specific messages.
 */
public abstract class Message {

    private PossibleMessages messageType;

    private String senderUsername;

    public PossibleMessages getMessageType() {
        return messageType;
    }

    public void setMessageType(PossibleMessages messageType) {
        this.messageType = messageType;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
}
