package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.DoubleToIntFunction;

/**
 * Class that communicates with clients. It has a reference to a server class and takes input from
 * clients to forward them to the server.
 */
public class SocketServer implements Runnable {

    /**
     * Reference to the server.
     */
    private final Server server;

    /**
     * Thread listening to this port to accept connections.
     */
    private final int port;

    /**
     * Reference to the JavaSE socket class that will be used to communicate.
     * {@link ServerSocket}
     */
    private ServerSocket serverSocket;

    /**
     * Default constructor.
     */
    public SocketServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Overrides empty run method from Thread class. Starts a thread to keep on listening
     * for new connections.
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Server.LOGGER.severe(() -> "Input error.");
        }

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Socket clientSocket = serverSocket.accept();

                System.out.println(clientSocket.getRemoteSocketAddress());

                clientSocket.setSoTimeout(5*1000);

                ClientHandler clientHandler = new ClientHandler(this, clientSocket);

                Thread clientThread = new Thread(clientHandler, "clientThread");
                clientThread.start();

            } catch (IOException e) {

                Server.LOGGER.severe(() -> "Client thread error.");
            }
        }
    }

    /**
     * adds a client to the server map.
     * @param nickname: name of the client.
     * @param clientHandler: handler associated to the client.
     */
    public void addClient(String nickname, ClientHandler clientHandler) {
        server.addNewClient(nickname, clientHandler);
    }

    /**
     * Whenever a message is forwarded to the serverSocket, it gets forwarded to the server.
     * @param message: message sent by the client.
     */
    public void onMessage(Message message) {
        server.onMessage(message);
    }

    /**
     * Forwards a disconnection request to the server.
     * @param clientHandler: associated to the client.
     */
    public void onDisconnect(ClientHandler clientHandler) {
        server.onDisconnect(clientHandler);
    }
}
