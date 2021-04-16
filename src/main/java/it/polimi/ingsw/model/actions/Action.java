package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

/**
 * Actions are messages sent by the GUI/CLI to the controller, they are validated and run if possible.
 * Action is abstract, and other specific actions extend it.
 */
public abstract class Action {

    /**
     * Method to verify the player can perform the action with the parameter
     * he decided and then set to the relative static action class
     *
     * @return true if the action can be done, otherwise false
     */
    public void isValid() throws InvalidPlayerException, InvalidGameStateException,
            GetResourceFromMarketException, BuyCardFromMarketException,
            NoMatchingRequisitesException, EndTurnException, LeaderCardException { }
}
