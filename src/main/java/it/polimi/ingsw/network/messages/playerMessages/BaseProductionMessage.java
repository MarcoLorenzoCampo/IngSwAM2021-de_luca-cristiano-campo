package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.Message;

public class BaseProductionMessage extends Message {
    private static final long serialVersionUID = -7523291661487374560L;
    private ResourceType input1;
    private ResourceType input2;
    private ResourceType output;

    public BaseProductionMessage(String username, ResourceType one, ResourceType two, ResourceType out){
        super.setMessageType(PossibleMessages.ACTIVATE_BASE_PRODUCTION);
        super.setSenderUsername(username);
        input1 = one;
        input2 = two;
        output = out;
    }

    public ResourceType getInput1() {
        return input1;
    }

    public ResourceType getInput2() {
        return input2;
    }

    public ResourceType getOutput() {
        return output;
    }
}
