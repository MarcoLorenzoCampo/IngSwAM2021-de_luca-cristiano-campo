package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.Message;

/**
 * Message sent by the player to tell the model what resource he needs to get in exchange
 * for the white marbles.
 *
 * It is sent only when two Leader cards with exchange effects are played.
 * {@link it.polimi.ingsw.model.market.leaderCards.MarbleExchangeLeaderCard}
 */
public class ExchangeResourceMessage extends Message {

    private static final long serialVersionUID = -1626244678088647633L;

    private final int index;
    private final ResourceType exchangeWithThis;

    public ExchangeResourceMessage(String nickname, ResourceType exchangeWithThis, int number) {
        super.setMessageType(PossibleMessages.EXCHANGE_RESOURCE);
        super.setSenderUsername(nickname);
        this.index = number;
        this.exchangeWithThis = exchangeWithThis;
    }

    public ResourceType getExchangeWithThis() {
        return this.exchangeWithThis;
    }
    public int getIndex(){return index;}
}
