package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
}
