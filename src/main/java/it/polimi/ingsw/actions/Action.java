package it.polimi.ingsw.actions;


import it.polimi.ingsw.exceptions.*;

/**
 * Actions are messages sent by the GUI/CLI to the controller, they are validated and run if possible.
 * Action is abstract, and other specific actions extend it.
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
