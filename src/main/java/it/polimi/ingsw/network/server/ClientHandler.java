package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable, IClientHandler {

    private final SocketServer socketServer;
    private final Socket clientSocket;

    private boolean isConnected;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream output;
    private ObjectInputStream input;

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

    @Override
    public void run() {
        try {
            handleNewConnection();
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * Handles the connection of a new client and keep listening to the socket for new messages.
     */
    private void handleNewConnection() throws IOException {

        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();

                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            Server.LOGGER.severe("Invalid stream");
        }
        clientSocket.close();
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void sendMessage(Message message) {

    }
}
