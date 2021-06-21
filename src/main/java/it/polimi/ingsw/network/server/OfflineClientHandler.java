package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;

public class OfflineClientHandler implements IClientHandler, Runnable {

    /**
     * Basic boolean check for connections.
     *
     * @return: true if connected
     */
    @Override
    public boolean isConnected() {
        return true;
    }

    /**
     * Removes a client from the server.
     */
    @Override
    public void disconnect() {
        //Useless offline
    }

    /**
     * Send data.
     *
     * @param message : data sent.
     */
    @Override
    public void sendMessage(Message message) {

    }

    /**
     * Disconnects a player when the name is already taken.
     */
    @Override
    public void sameNameDisconnect() {

    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        handleUserMessages();
    }

    private void handleUserMessages() {

    }
}
