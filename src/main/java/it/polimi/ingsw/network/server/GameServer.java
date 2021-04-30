package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.parsers.ServerConfigParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Setting up the server based on the input given by command line:
 * Accepted command format:
 *
 * 3 arguments:
 *      user@user:~$ java -jar client.jar <"cli"/"gui"> <port_number> <game_mode>
 *
 * For non-specified options, a Json config file will be used. Every non recognized input will stop the
 * setup process and a default config file will be used instead.
 * (Other parameters can be edited from the config file as well)
 *
 * Accepted commands (not case sensitive):
 * <"cli"/"gui"> -> "cli" or "gui".
 * <port_number> -> integer from 1024 to Integer.MAX_VALUE.
 * <game_mode> -> "online_single_player" or "online_multiplayer".
 */

public class GameServer {

    private final ArrayList<Socket> connectedClients;
    private final ArrayList<ClientHandler> handlers;
    private ServerSocket serverSocket;

    private final String viewType;
    private final int socket_port;
    private final int gameMode;

    private final int start_time;
    private final int move_time;
    private final GameManager gameManager;

    private boolean shouldRun = true;
    private final Timer playingTimer;

    /**
     * Starts a server for a new game.
     * @param viewType: cli or gui
     * @param socket_port: server's port to listen on
     * @param gameMode: single or multi player game online
     */
    public GameServer(String viewType, int socket_port, int gameMode, int start_time, int move_time) {

        this.viewType = viewType;
        this.socket_port = socket_port;
        this.move_time = move_time;
        this.start_time = start_time;
        this.gameMode = gameMode;

        this.playingTimer = new Timer();
        this.gameManager = new GameManager(gameMode, this);

        System.out.println(viewType  + "  " + socket_port +  "  " + move_time +  "  " + start_time +  "  " + gameMode);
        handlers = new ArrayList<>();
        connectedClients = new ArrayList<>();

        serverStart();
    }

    public static void main(String[] args) {

        int[] configs = ServerConfigParser.readServerConfig();

        if(args.length == 3) {
            if(args[2].equals("multiplayer")) {
                new GameServer(args[0], Integer.parseInt(args[1]), 2, configs[2], configs[3]);
            } else {
                new GameServer(args[0], Integer.parseInt(args[1]), 1, configs[2], configs[3]);
            }
        } else {
            defaultServerSetup(configs);
        }
    }

    private void serverStart() {

        try {
            this.serverSocket = new ServerSocket(socket_port);
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("GameServer ready to accept connections:");

        /* running an infinite loop to keep on accepting new clients */
        while(shouldRun) {

            try {
                /* a new socket is assigned to every new connection request */
                Socket socket = serverSocket.accept();

                /* adding new clients to a list of accepted sockets */
                connectedClients.add(socket);

                System.out.println(socket.getRemoteSocketAddress() + " connected");

                /* a new client handler object is assigned to the newly accepted connection */
                ClientHandler handler = new ClientHandler(socket, this);

                /* adding the new client to a list of handlers */
                handlers.add(handler);

                /* thread with the new connection starts */
                new Thread(handler).start();

            } catch(IOException e) {
                break;
            }
        }

        try {
            System.out.println("GameServer arrested.");
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void defaultServerSetup(int[] configs) {
        new GameServer("cli", configs[0], configs[1], configs[2], configs[3]);
    }

    public void sendMessageToAllClients(String message) throws IOException {
        for(ClientHandler handler : handlers) {
            handler.sendMessageToClient(message);
        }
    }

    public String getViewType() {
        return viewType;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Timer getPlayingTimer() {
        return playingTimer;
    }

    public int getGameMode() {
        return gameMode;
    }

    public int getSocket_port() {
        return socket_port;
    }

    public int getStart_time() {
        return start_time;
    }

    public int getMove_time() {
        return move_time;
    }

    public void stopServerRun() {
        this.shouldRun = false;
    }

    public void removePlayerFromGame(Socket playerWhoLeft) throws IOException {
        connectedClients.remove(playerWhoLeft);
        System.out.println("Current clients: " + handlers.size()+1);

        for (Socket socket : connectedClients) {
            System.out.println(socket.getRemoteSocketAddress());
        }

        if(connectedClients.isEmpty()) {
            serverSocket.close();
        }
    }
}
