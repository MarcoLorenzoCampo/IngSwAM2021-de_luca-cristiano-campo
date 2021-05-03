package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

public class OneNumberMessage extends Message{
    private int index;

    public OneNumberMessage(String username, PossibleMessages type, int number){
        super.setSenderUsername(username);
        super.setMessageType(type);
        index = number;
    }
}
