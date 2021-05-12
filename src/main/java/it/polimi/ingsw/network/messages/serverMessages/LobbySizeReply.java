package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

import java.io.Serializable;

public class LobbySizeReply extends Message implements Serializable {

    private static final long serialVersionUID = -4492187415116157326L;

    public LobbySizeReply() {
        super.setMessageType(PossibleMessages.GAME_SIZE);
    }
}
