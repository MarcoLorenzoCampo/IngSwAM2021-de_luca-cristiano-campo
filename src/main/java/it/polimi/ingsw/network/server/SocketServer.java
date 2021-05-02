package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class that communicates with clients. It has a reference to a server class and takes input from
 * clients to forward them to the server.
 */
public class SocketServer implements Runnable {

    private final Server server;
    private final int port;
    private ServerSocket serverSocket;

    public SocketServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Overrides empty run method from Thread class. Starts a thread to keep on listening
     * for new clients request of connection.
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Socket clientSocket = serverSocket.accept();

                clientSocket.setSoTimeout(5000);

                ClientHandler clientHandler = new ClientHandler(this, clientSocket);

                Thread clientThread = new Thread(clientHandler, "clientThread");
                clientThread.start();

            } catch (IOException e) {
                e.printStackTrace();
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
        server.onDisconnection(clientHandler);
    }
}
