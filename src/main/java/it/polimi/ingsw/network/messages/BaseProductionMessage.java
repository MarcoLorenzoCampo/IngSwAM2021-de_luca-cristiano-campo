package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;
import it.polimi.ingsw.enumerations.ResourceType;

public class BaseProductionMessage extends Message{
    private ResourceType input1;
    private ResourceType input2;
    private ResourceType output;

    public BaseProductionMessage(String username, ResourceType one, ResourceType two, ResourceType out){
        super.setMessageType(PossiblePlayerMessages.ACTIVATE_BASE_PRODUCTION);
        super.setSenderUsername(username);
        input1 = one;
        input2 = two;
        output = out;
    }
}
