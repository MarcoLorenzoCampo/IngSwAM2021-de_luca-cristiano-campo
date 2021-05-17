package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.Message;

import java.util.LinkedList;
import java.util.List;

public class SetupResourceAnswer extends Message {
    private static final long serialVersionUID = 6270608376298079943L;
    private final int resourcesToSet;
    private final LinkedList<ResourceType> resourceTypes;

    public  SetupResourceAnswer (String nickname, int number, LinkedList<ResourceType> chosen){
        super.setSenderUsername(nickname);
        super.setMessageType(PossibleMessages.SETUP_RESOURCES);
        this.resourcesToSet = number;
        this.resourceTypes = chosen;
    }

    public int getResourcesToSet() {
        return resourcesToSet;
    }

    public List<ResourceType> getResourceTypes() {
        return resourceTypes;
    }
}
