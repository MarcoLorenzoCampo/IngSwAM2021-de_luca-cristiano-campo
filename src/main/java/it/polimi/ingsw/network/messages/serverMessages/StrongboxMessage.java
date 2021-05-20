package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.Message;

import java.util.HashMap;

public class StrongboxMessage extends Message {
    private static final long serialVersionUID = -3827032281162691179L;
    private  HashMap<ResourceType, Integer> strongbox;

    public StrongboxMessage(HashMap<ResourceType, Integer> real_strongbox){
        super.setSenderUsername("SERVER_MESSAGE");
        super.setMessageType(PossibleMessages.STRONGBOX);
        this.strongbox = new HashMap<>();
        this.strongbox = real_strongbox;
    }

    public  HashMap<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }


}
