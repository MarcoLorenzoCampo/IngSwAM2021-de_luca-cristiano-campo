package it.polimi.ingsw.exceptions;

public class EndTurnException extends Exception {

    public EndTurnException(String currentPlayer) {
        super(currentPlayer + " just finished the turn.");
    }
}
