package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.eventHandlers.ViewObserver;
import it.polimi.ingsw.network.messages.Message;

import java.util.logging.Logger;

public interface IClient {
    /**
     * Default logger.
     */
    Logger clientLogger = Logger.getLogger(Client.class.getName());

    /**
     * Method to run an asynchronous thread to work as input listener, waiting for messages
     * sent by the server.
     */
    void readMessage();

    /**
     * Method to send messages to the server's socket.
     * @param message: message to send.
     */
    void sendMessage(Message message);

    /**
     * Removes a client from the server's socket.
     */
    void disconnect();

    /**
     * Enable periodic ping function.
     * @param enable: enabled/disabled.
     */
    void startPing(boolean enable);
}
