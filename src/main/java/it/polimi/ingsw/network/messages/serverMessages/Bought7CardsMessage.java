package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

/**
 * Notifies the observer a player has bought the maximum number of cards, starting the end game
 * turn.
 */
public class Bought7CardsMessage extends Message {
    private static final long serialVersionUID = -5051744373396709476L;

    public Bought7CardsMessage() {
        super.setMessageType(PossibleMessages.BOUGHT_7_CARDS);
        super.setSenderUsername("SERVER_MESSAGE");
    }
}
