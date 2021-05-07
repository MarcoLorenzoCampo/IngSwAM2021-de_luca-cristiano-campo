package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

public class TwoIntMessage extends Message {
    private final int firstNumber;
    private final int secondNumber;

    public TwoIntMessage(String username, PossibleMessages type, int first, int second){
        super.setMessageType(type);
        super.setSenderUsername(username);
        firstNumber = first;
        secondNumber = second;
    }
}
