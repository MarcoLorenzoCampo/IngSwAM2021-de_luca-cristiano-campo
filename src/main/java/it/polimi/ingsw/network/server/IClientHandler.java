package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.Message;

/**
 * Interface for client handlers.
 */
public interface IClientHandler {

    /**
     * Basic boolean check for connections.
     * @return: true if connected
     */
    boolean isConnected();

    /**
     * Removes a client from the server.
     */
    void disconnect();

    /**
     * Send data.
     * @param message: data sent.
     */
    void sendMessage(Message message);

    /**
     * Disconnects a player when the name is already taken.
     */
    void sameNameDisconnect();
}
