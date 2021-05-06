package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Class to
 */
public class Client implements Runnable {

    /**
     * Default logger.
     */
    public static final Logger clientLogger = Logger.getLogger(Client.class.getName());

    /**
     * Socket that connects and communicates with the server's socket.
     */
    private final Socket clientSocket;

    /**
     * Streams to exchange messages {@link it.polimi.ingsw.network.messages.Message} with
     * the server's socket.
     */
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Thread to read asynchronous messages sent by the server.
     */
    private Thread readerThread;

    /**
     * Client constructor.
     * @param port: server's socket.
     * @param IP_Address: IP address.
     */
    public Client(int port, String IP_Address) {

        this.clientSocket = new Socket();

        try {
            this.clientSocket.connect(new InetSocketAddress(IP_Address, port));

        } catch (IOException e) {

                clientLogger.severe(() -> "Unable to connect to the server. Connection refused.");
            }

        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

        } catch (IOException e) {

            clientLogger.severe(() -> "Couldn't connect to the host.");
        }

        readerThread = new Thread();
        readerThread.start();
    }

    /**
     * Overriding default run() method for the thread.
     */
    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()) {

            Message message;

            try {
                message = (Message) input.readObject();
                clientLogger.info("Received: " + message + " from server.");
            } catch (IOException e) {
                clientLogger.severe(() -> "Communication error. Critical error.");
            } catch (ClassNotFoundException e) {
                clientLogger.severe(() -> "Got an unexpected Object from server. Critical error.");

            } finally {

                try {
                    clientSocket.close();
                } catch (IOException e) {
                    clientLogger.severe(() -> "Socket error. Critical error.");
                }

                disconnect();
            }
        }
    }

    /**
     * Method to send messages to the server's socket.
     * @param message: message to send.
     */
    public void sendMessage(Message message) {

        try {
            output.writeObject(message);
            output.reset();

        } catch (IOException e) {

            clientLogger.severe(() -> "Unable to send the message.");
            disconnect();
        }

    }

    /**
     * Method to disconnect the client from the server in case of errors.
     */
    public void disconnect() {

        if(!clientSocket.isClosed()) {
            try {

                clientSocket.close();
            } catch (IOException e) {

                clientLogger.severe(() -> "Could not disconnect. Critical error.");
            }
        }
    }
}
