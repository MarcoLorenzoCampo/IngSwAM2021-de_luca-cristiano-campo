package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;


public class LorenzoFaithTrackMessage extends Message {
    private static final long serialVersionUID = 7391706320979798014L;
    private final int position;

    public LorenzoFaithTrackMessage(int position){
        super.setSenderUsername("SERVER_MESSAGE");
        super.setMessageType(PossibleMessages.LORENZO_FAITHTRACK);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
