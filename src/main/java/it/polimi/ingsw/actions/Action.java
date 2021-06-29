package it.polimi.ingsw.actions;


import it.polimi.ingsw.exceptions.*;

/**
 * Actions are objects sent from the controller to the player, each action represents a series of methods
 * to change the model, each action holds the information necessary to make these methods work.
 * They map user actions to model updates
 */
public abstract class Action implements Visitable {

    private String actionSender;

    public void setActionSender(String actionSender) {
        this.actionSender = actionSender;
    }

    public String getActionSender() {
        return actionSender;
    }
}
