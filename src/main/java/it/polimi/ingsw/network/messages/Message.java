package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;

import java.io.Serializable;

/**
 * Abstract concept of Objects to be exchanged between clients and server.
 * To be extended by specific messages.
 */
public abstract class Message implements Serializable {

    private static final long serialVersionUID = 4990329164082898297L;

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

    @Override
    public String toString() {
        return "Message{" +
                "nickname=" + senderUsername +
                ", messageType=" + messageType +
                '}';
    }
}
