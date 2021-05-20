package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class DiscardedResourceMessage extends Message {

    private static final long serialVersionUID = -4164714297931839034L;

    public DiscardedResourceMessage() {
        super.setMessageType(PossibleMessages.DISCARDED_RESOURCE);
        super.setSenderUsername("SERVER_MESSAGE");
    }
}
