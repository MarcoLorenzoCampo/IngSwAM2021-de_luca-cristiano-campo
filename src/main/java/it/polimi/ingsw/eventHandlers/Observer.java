package it.polimi.ingsw.eventHandlers;

import it.polimi.ingsw.network.messages.Message;

/**
 * Interface to implement the generic update() method.
 * Re-defines the deprecated Observer java class.
 */
public interface Observer {

    void update(Message message);
}
