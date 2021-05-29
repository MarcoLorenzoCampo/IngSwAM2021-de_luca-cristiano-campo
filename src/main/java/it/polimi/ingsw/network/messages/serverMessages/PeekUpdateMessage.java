package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.messages.Message;

import java.util.List;
import java.util.Map;

/**
 * Message that contains reduced info about enemies to be sent by the server each turn.
 */
public class PeekUpdateMessage extends Message {
    private static final long serialVersionUID = -5781402945077556744L;

    private final String name;
    private final int faithPosition;
    private final Map<ResourceType, Integer> inventory;
    private final List<EffectType> cards;

    public PeekUpdateMessage(String name, int faithPosition, Map<ResourceType, Integer> inventory, List<EffectType> cards) {

        super.setMessageType(PossibleMessages.PEEK_MESSAGE);
        super.setSenderUsername("SERVER_MESSAGE");

        this.cards = cards;
        this.faithPosition = faithPosition;
        this.inventory = inventory;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getFaithPosition() {
        return faithPosition;
    }

    public Map<ResourceType, Integer> getInventory() {
        return inventory;
    }

    public List<EffectType> getCards() {
        return cards;
    }
}
