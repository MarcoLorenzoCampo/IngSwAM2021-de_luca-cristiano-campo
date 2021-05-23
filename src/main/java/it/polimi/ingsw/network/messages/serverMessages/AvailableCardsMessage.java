package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

/**
 * Contains the information about the available production cards. Is shown when a player updates
 * the available ones buying one or Lorenzo discards some of them during the single player game.
 */
public class AvailableCardsMessage extends Message {

    private static final long serialVersionUID = -3293539323683782350L;

    private final String reducedAvailableCards;

    public AvailableCardsMessage(String reducedAvailableCards) {

        super.setMessageType(PossibleMessages.AVAILABLE_PRODUCTION_CARDS);
        super.setSenderUsername("SERVER_MESSAGE");

        this.reducedAvailableCards = reducedAvailableCards;
    }

    public String getReducedAvailableCards() {
        return reducedAvailableCards;
    }
}
