package it.polimi.ingsw.exceptions;

public class NoMatchingRequisitesException extends Exception {

    public NoMatchingRequisitesException() {
        super("You don't match the correct requisites to buy/place this card!");
    }
}
