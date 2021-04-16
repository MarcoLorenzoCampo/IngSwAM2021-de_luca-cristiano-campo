package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.model.actions.EndTurnAction;

public class EndTurnException extends Exception {

    public EndTurnException(String currentPlayer) {
        super(currentPlayer + " just finished the turn.");
    }
}
