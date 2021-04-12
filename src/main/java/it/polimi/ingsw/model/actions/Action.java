package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;

public interface Action {

    /**
     * Method to verify the player can perform the action with the parameter
     * he decided and then set to the relative static action class
     *
     * @return true if the action can be done, otherwise false
     */
    void isValid() throws InvalidPlayerException, InvalidActionException;
}
