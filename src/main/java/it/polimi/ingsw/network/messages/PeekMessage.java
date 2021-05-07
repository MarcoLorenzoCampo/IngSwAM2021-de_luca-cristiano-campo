package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

public class PeekMessage extends Message {

    private final String enemyToPeek;

    public PeekMessage(String username, String enemyToPeek){
        super.setMessageType(PossibleMessages.PEEK_ENEMY);
        super.setSenderUsername(username);
        this.enemyToPeek = enemyToPeek;
    }
}
