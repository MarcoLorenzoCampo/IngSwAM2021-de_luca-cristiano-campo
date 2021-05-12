package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.Message;

public class ChosenResourceMessage extends Message {
    private static final long serialVersionUID = -6019798360641426232L;
    private ResourceType chosenResource;

    public ChosenResourceMessage(String username, ResourceType type){
        super.setSenderUsername(username);
        super.setMessageType(PossibleMessages.RESOURCE);
        chosenResource = type;
    }
}
