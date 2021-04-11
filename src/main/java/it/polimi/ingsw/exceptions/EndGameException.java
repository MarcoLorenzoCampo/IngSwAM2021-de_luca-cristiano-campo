package it.polimi.ingsw.exceptions;

/**
 * Thrown to notify the controller game is ending
 */
public class EndGameException extends Exception{

    public EndGameException(String message) {
        super(message);
    }
}
