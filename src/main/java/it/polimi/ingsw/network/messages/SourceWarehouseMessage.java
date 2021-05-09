package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;

public class SourceWarehouseMessage extends Message {
    public SourceWarehouseMessage(String username){
        super.setSenderUsername(username);
        super.setMessageType(PossiblePlayerMessages.SOURCE_WAREHOUSE);
    }
}
