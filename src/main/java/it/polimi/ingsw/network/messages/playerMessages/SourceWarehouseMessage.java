package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;
import it.polimi.ingsw.network.messages.Message;

public class SourceWarehouseMessage extends Message {
    private static final long serialVersionUID = -4493274709215241076L;

    public SourceWarehouseMessage(String username){
        super.setSenderUsername(username);
        super.setMessageType(PossiblePlayerMessages.SOURCE_WAREHOUSE);
    }
}
