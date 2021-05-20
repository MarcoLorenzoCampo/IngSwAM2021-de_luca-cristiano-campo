package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class YourTurnMessage extends Message {

    private static final long serialVersionUID = -6987829040992279042L;
    private final String message;

    public YourTurnMessage(String message){
        super.setMessageType(PossibleMessages.YOUR_TURN);
        this.message = message;
        super.setSenderUsername("SERVER_MESSAGE");
    }

    public String getMessage() {
        return message;
    }
}
