package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

public class SourceWarehouseMessage extends Message {
    public SourceWarehouseMessage(String username){
        super.setSenderUsername(username);
        super.setMessageType(PossibleMessages.SOURCE_WAREHOUSE);
    }
}
