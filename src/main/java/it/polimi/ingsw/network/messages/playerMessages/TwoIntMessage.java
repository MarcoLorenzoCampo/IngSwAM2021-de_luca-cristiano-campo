package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class TwoIntMessage extends Message {
    private static final long serialVersionUID = -4713810255070437176L;
    private final int firstNumber;
    private final int secondNumber;

    public TwoIntMessage(String username, PossibleMessages type, int first, int second){
        super.setMessageType(type);
        super.setSenderUsername(username);
        firstNumber = first;
        secondNumber = second;
    }
}
