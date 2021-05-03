package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

public class SourceStrongboxMessage extends Message{
    public SourceStrongboxMessage(String username){
        super.setSenderUsername(username);
        super.setMessageType(PossibleMessages.SOURCE_STRONGBOX);
    }
}
