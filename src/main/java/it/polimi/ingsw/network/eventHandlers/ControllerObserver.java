package it.polimi.ingsw.network.eventHandlers;

import it.polimi.ingsw.network.messages.Message;

/**
 * Interface to implement the generic update() method.
 * Serves as a special observer, is implemented by the controller who observes the model.
 */
public interface ControllerObserver {

    void controllerUpdate(Message message);
}
