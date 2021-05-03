package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

public class TwoNumberMessage extends Message {
    private int firstNumber;
    private int secondNumber;

    public TwoNumberMessage (String username, PossibleMessages type, int first, int second){
        super.setMessageType(type);
        super.setSenderUsername(username);
        firstNumber = first;
        secondNumber = second;
    }
}
