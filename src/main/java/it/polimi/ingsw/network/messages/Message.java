package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;

import java.io.Serializable;

/**
 * Abstract concept of Objects to be exchanged between clients and server.
 * To be extended by specific messages.
 */
public abstract class Message implements Serializable {

    private PossiblePlayerMessages messageType;

    private String senderUsername;

    public PossiblePlayerMessages getMessageType() {
        return messageType;
    }

    public void setMessageType(PossiblePlayerMessages messageType) {
        this.messageType = messageType;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
}
