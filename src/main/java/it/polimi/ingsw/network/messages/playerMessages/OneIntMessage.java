package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class OneIntMessage extends Message {
    private static final long serialVersionUID = -3999814384830435938L;
    private int index;

    public OneIntMessage(String username, PossibleMessages type, int number){
        super.setSenderUsername(username);
        super.setMessageType(type);
        index = number;
    }

    public int getIndex() {
        return index;
    }
}
