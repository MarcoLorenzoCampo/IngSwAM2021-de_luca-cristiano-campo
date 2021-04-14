package it.polimi.ingsw.exceptions;

public class InvalidGameStateException extends Exception {

    public InvalidGameStateException() {
        super("Invalid game state to perform this action!");
    }
}
