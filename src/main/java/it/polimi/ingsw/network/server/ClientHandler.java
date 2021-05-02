package it.polimi.ingsw.network.server;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class to handle clients server-side when one connects to the server's socket.
 * Implements Runnable to be able to execute threads (overriding run()) and IClientHandler.
 */
public class ClientHandler implements Runnable, IClientHandler {

    private final SocketServer socketServer;
    private final Socket clientSocket;

    private boolean isConnected;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Custom constructor.
     * @param socketServer: reference to the server's socket to communicate
     *                    with the server in case of disconnection.
     * @param clientSocket: reference to the client's socket to communicate via Output and Input streams.
     */
    public ClientHandler(SocketServer socketServer, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.socketServer = socketServer;

        this.inputLock = new Object();
        this.outputLock = new Object();

        this.isConnected = true;

        try {
            this.output = new ObjectOutputStream(clientSocket.getOutputStream());
            this.input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overriding default run() method.
     */
    @Override
    public void run() {
        try {
            handleUserMessages();
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * After the client is connected and associated to a handler, a specific thread is run
     * to keep up with the messages he sends.
     *
     * There may be ping messages that have to be ignored.
     */
    private void handleUserMessages() throws IOException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();

                    if(message.getMessageType().equals(PossibleMessages.PING_MESSAGE)) {
                        continue;
                    }



                    //handle messages here;
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            Server.LOGGER.severe("Invalid stream");
        }
        clientSocket.close();
    }

    /**
     * Method to check the user's connection status.
     * @return: true if it's still connected, false otherwise
     */
    @Override
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Disconnects a client from the server's socket.
     */
    @Override
    public void disconnect() {
        if (isConnected) {
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                Server.LOGGER.severe(e.getMessage());
            }
            isConnected = false;
            Thread.currentThread().interrupt();

            socketServer.onDisconnect(this);
        }
    }

    /**
     * Method to send messages to the client.
     * @param message: data sent.
     */
    @Override
    public void sendMessage(Message message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.reset();
                Server.LOGGER.info(() -> "Sent: " + message);
            }
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
            disconnect();
        }
    }
}
