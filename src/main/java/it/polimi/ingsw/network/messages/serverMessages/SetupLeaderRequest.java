package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class SetupLeaderRequest extends Message {
    private static final long serialVersionUID = 1174500934280913895L;

    public SetupLeaderRequest(){
        super.setMessageType(PossibleMessages.SETUP_LEADERS);
        super.setSenderUsername("SERVER_MESSAGE");
    }
}
