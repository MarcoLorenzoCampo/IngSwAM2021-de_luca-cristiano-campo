package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the information about the available production cards. Is shown when a player updates
 * the available ones buying one or Lorenzo discards some of them during the single player game.
 */
public class AvailableCardsMessage extends Message {

    private static final long serialVersionUID = -3293539323683782350L;

    private final List<ProductionCard> availableCards;

    public AvailableCardsMessage(List<ProductionCard> availableCards) {

        super.setMessageType(PossibleMessages.AVAILABLE_PRODUCTION_CARDS);

        this.availableCards = availableCards;
    }
}
