package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class LeaderCardMessage extends Message {

    private static final long serialVersionUID = -5649155565123346151L;
    private final List<LeaderCard> available;

    public LeaderCardMessage (List<LeaderCard> inModel){
        super.setMessageType(PossibleMessages.AVAILABLE_LEADERS);
        super.setSenderUsername("SERVER_MESSAGE");
        available = inModel;
    }

    public List<LeaderCard> getAvailable() {
        return available;
    }
}
