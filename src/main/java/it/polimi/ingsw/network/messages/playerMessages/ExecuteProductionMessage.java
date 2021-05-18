package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class ExecuteProductionMessage extends Message {
    private static final long serialVersionUID = -6477926747494295210L;

    public ExecuteProductionMessage(String username){
        super.setMessageType(PossibleMessages.EXECUTE_PRODUCTION);
        super.setSenderUsername(username);
    }
}
