package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;

public class ChosenResourceMessage extends Message{
    private ResourceType chosenResource;

    public ChosenResourceMessage(String username, ResourceType type){
        super.setSenderUsername(username);
        super.setMessageType(PossibleMessages.RESOURCE);
        chosenResource = type;
    }
}
