package it.polimi.ingsw.model.actions;

public interface Action {

    /**
     * Method to verify the player can perform the action with the parameter
     * he decided and then set to the relative static action class
     *
     * @return true if the action can be done, otherwise false
     */
    boolean isValid();

    /**
     * Makes a player do the action he chose
     */
    void runAction();
}
