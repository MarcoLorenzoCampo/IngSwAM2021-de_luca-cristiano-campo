package it.polimi.ingsw.exceptions;

/**
 * Thrown in the setup phase of the game if a player's name
 * is already taken
 */
public class NameTakenException extends Exception{

    public NameTakenException(String takenName) {
        super(takenName + " is already taken, choose a different one!");
    }
}
