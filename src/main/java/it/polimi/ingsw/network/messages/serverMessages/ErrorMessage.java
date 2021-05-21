package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class ErrorMessage extends Message {
    private static final long serialVersionUID = -3513490851483807040L;

    private final String errorMessage;

    public ErrorMessage(String errorMessage) {

        super.setMessageType(PossibleMessages.ERROR);

        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
