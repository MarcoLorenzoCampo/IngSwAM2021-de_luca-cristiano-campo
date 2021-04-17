package it.polimi.ingsw.exceptions;

/**
 * Thrown to notify ResourceManager that that type of resorce cannot be placed, thus it
 * needs to discard all resources obtained of that type
 */
public class DiscardResourceException extends Exception {

    public DiscardResourceException() {
        super("Discarded a resource! Enemies will move one tile.");
    }
}
