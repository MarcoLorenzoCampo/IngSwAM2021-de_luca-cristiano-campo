package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;

public class OfflineClient implements IClient {

    /**
     * Method to run an asynchronous thread to work as input listener, waiting for messages
     * sent by the server.
     */
    @Override
    public void readMessage() {

    }

    /**
     * Method to send messages to the server's socket.
     *
     * @param message : message to send.
     */
    @Override
    public void sendMessage(Message message) {

    }

    /**
     * Removes a client from the server's socket.
     */
    @Override
    public void disconnect() {

    }

    /**
     * Enable periodic ping function.
     *
     * @param enable : enabled/disabled.
     */
    @Override
    public void startPing(boolean enable) {

    }
}
