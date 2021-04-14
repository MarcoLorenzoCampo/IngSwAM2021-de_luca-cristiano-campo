package it.polimi.ingsw.exceptions;

public class InvalidPlayerException extends Exception {

    public InvalidPlayerException() {
        super("Wrong player data!");
    }
}
