package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class SetupResourcesRequest extends Message {

    private static final long serialVersionUID = -1261190020497561944L;
    private int numberOfResources;

    public SetupResourcesRequest(int number){
        super.setMessageType(PossibleMessages.SETUP_RESOURCES);
        super.setSenderUsername("SERVER");
        this.numberOfResources = number;
    }

    public int getNumberOfResources(){
        return numberOfResources;
    }
}
