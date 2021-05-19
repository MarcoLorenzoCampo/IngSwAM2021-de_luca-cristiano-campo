package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class EndTurnMessage extends Message {

    private static final long serialVersionUID = 1497214834887983386L;

    public EndTurnMessage(String senderusername){
        super.setMessageType(PossibleMessages.END_TURN);
        super.setSenderUsername(senderusername);
    }
}
