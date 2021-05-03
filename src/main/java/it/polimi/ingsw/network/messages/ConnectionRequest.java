package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

public class ConnectionRequest extends Message{

    public ConnectionRequest(String username){
        super.setMessageType(PossibleMessages.CONNECTION);
        super.setSenderUsername(username);
    }
}
