package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class EndGameMessage extends Message {
    private static final long serialVersionUID = 8227560973340913575L;
    public EndGameMessage (){
        super.setMessageType(PossibleMessages.END_GAME);
    }

}
