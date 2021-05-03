package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;

public class ExtraProductionMessage extends Message{
    private int index;
    private ResourceType output;

    public ExtraProductionMessage(String username, int number, ResourceType out){
        super.setSenderUsername(username);
        super.setMessageType(PossibleMessages.ACTIVATE_EXTRA_PRODUCTION);
        index= number;
        output = out;
    }
}
