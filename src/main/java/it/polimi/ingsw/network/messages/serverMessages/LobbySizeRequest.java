package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class LobbySizeRequest extends Message {

    private static final long serialVersionUID = -4689103654553569272L;

    public LobbySizeRequest() {
        super.setMessageType(PossibleMessages.LOBBY_SIZE_REQUEST);
        super.setSenderUsername("SERVER_MESSAGE");
    }
}
