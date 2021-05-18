package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class WinMessage extends Message {

    private static final long serialVersionUID = 7934350788489757503L;

    private final String message;

    public WinMessage(String message) {
        super.setSenderUsername("SERVER_MESSAGE");
        super.setMessageType(PossibleMessages.WIN_MESSAGE);

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
