package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.eventHandlers.observers.Observable;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to handle client operations.
 */
public class Client extends Observable implements IClient {

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
     * Client constructor. Connects the client to the server's socket.
     *
     * @param port:       server's socket.
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

        readMessage();
    }

    @Override
    public void readMessage() {

        ExecutorService listener = Executors.newSingleThreadExecutor();

        listener.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = null;

                try {
                    message = (Message) input.readObject();

                    clientLogger.info("Received: " + message + " from server.");
                } catch (IOException e) {

                    clientLogger.severe(() -> "Communication error. Critical error.");
                    disconnect();
                    listener.shutdown();

                } catch (ClassNotFoundException e) {

                    clientLogger.severe(() -> "Got an unexpected Object from server. Critical error.");
                    disconnect();
                    listener.shutdown();
                }

                notifyObserver(message);
            }
        });
    }

    @Override
    public void sendMessage(Message message) {

        try {
            output.writeObject(message);
            output.reset();

        } catch (IOException e) {

            clientLogger.severe(() -> "Unable to send the message.");
            disconnect();
        }
    }

    @Override
    public void disconnect() {

        if (!clientSocket.isClosed()) {
            try {

                clientSocket.close();
            } catch (IOException e) {

                clientLogger.severe(() -> "Could not disconnect. Critical error.");
            }
        }
    }

    @Override
    public void periodicPing() {

    }

    //------------------------------------------ MAIN METHOD -----------------------------------------------

    /**
     * Main method of the client class, it can be launched in both cli or gui mode.
     * The default option is cli, it can be used in gui mode by adding "-gui" when running the jar file.
     *
     * user @ user:$ client.jar -cli
     */
    public static void main(String[] args) {

        boolean cli = true;

        for (String arg : args) {
            if (arg.equalsIgnoreCase("-gui")) {
                cli = false;
                break;
            }
        }

        if (cli) {
            //mock client for testing purposes
        } else {
            //launch gui

        }
    }
}