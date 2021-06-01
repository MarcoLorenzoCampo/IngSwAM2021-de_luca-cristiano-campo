package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

/**
 * Message sent by the model to the SinglePlayer lobby manager to notify Lorenzo
 * removed a whole sub-deck.
 * The game must end here.
 */
public class NoMoreCardsMessage extends Message {
    private static final long serialVersionUID = -4976245927482339016L;

    public NoMoreCardsMessage() {
        super.setMessageType(PossibleMessages.NO_MORE_CARDS);
        super.setSenderUsername("SERVER_MESSAGE");
    }
}
