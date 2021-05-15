package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

/**
 * Generic string message sent from server.
 */
public class GenericMessageFromServer extends Message {
    private static final long serialVersionUID = 2736909234455060678L;

    private final String serverString;

    public GenericMessageFromServer(String serverString) {
        super.setMessageType(PossibleMessages.GENERIC_SERVER_MESSAGE);
        super.setSenderUsername("SERVER_MESSAGE");

        this.serverString = serverString;
    }

    public String getServerString() {
        return serverString;
    }
}
