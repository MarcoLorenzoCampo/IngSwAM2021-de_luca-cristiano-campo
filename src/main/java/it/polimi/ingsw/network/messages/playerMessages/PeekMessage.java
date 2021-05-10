package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;
import it.polimi.ingsw.network.messages.Message;

public class PeekMessage extends Message {

    private static final long serialVersionUID = 4098149122713160285L;
    private final String enemyToPeek;

    public PeekMessage(String username, String enemyToPeek){
        super.setMessageType(PossiblePlayerMessages.PEEK_ENEMY);
        super.setSenderUsername(username);
        this.enemyToPeek = enemyToPeek;
    }
}
