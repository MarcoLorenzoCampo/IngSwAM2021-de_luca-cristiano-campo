package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.Message;

public class ExtraProductionMessage extends Message {
    private static final long serialVersionUID = -3170018442335915626L;
    private int index;
    private ResourceType output;

    public ExtraProductionMessage(String username, int number, ResourceType out){
        super.setSenderUsername(username);
        super.setMessageType(PossibleMessages.ACTIVATE_EXTRA_PRODUCTION);
        index= number;
        output = out;
    }

    public ResourceType getOutput() {
        return output;
    }

    public int getIndex() {
        return index;
    }
}
