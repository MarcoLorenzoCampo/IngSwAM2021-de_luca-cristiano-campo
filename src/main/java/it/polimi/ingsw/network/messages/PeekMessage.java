package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;

public class PeekMessage extends Message {

    private final String enemyToPeek;

    public PeekMessage(String username, String enemyToPeek){
        super.setMessageType(PossiblePlayerMessages.PEEK_ENEMY);
        super.setSenderUsername(username);
        this.enemyToPeek = enemyToPeek;
    }
}
