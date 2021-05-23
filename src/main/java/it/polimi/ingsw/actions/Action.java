package it.polimi.ingsw.actions;


import it.polimi.ingsw.exceptions.*;

/**
 * Actions are messages sent by the GUI/CLI to the controller, they are validated and run if possible.
 * Action is abstract, and other specific actions extend it.
 */
public abstract class Action implements Visitable {

    private String actionSender;

    /**
     * Method to verify the player can perform the action with the parameter
     * he decided and then set to the relative static action class
     */
    public void isValid() throws InvalidPlayerException, InvalidGameStateException,
            GetResourceFromMarketException, BuyCardFromMarketException,
            NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
    }

    public void setActionSender(String actionSender) {
        this.actionSender = actionSender;
    }

    public String getActionSender() {
        return actionSender;
    }
}
