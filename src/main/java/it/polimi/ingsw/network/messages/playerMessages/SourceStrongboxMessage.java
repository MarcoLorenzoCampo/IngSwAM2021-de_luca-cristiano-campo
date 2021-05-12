package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class SourceStrongboxMessage extends Message {

    private static final long serialVersionUID = 3870979900865901401L;

    public SourceStrongboxMessage(String username){
        super.setSenderUsername(username);
        super.setMessageType(PossibleMessages.SOURCE_STRONGBOX);
    }
}
