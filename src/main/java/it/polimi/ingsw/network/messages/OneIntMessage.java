package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;

public class OneIntMessage extends Message{
    private int index;

    public OneIntMessage(String username, PossiblePlayerMessages type, int number){
        super.setSenderUsername(username);
        super.setMessageType(type);
        index = number;
    }
}
