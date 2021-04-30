package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.parsers.ServerConfigParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Setting up the server based on the input given by command line:
 * Accepted command format:
 *
 * 3 arguments:
 *      user@user:~$ java -jar client.jar <"cli"/"gui"> <port_number> <game_mode> *
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

public class MultiEchoServer {

    private ServerSocket serverSocket;

    private final String viewType;
    private final int socket_port;
    private final int gameMode;

    private final int start_time;
    private final int move_time;
    private final GameManager gameManager;

    private final Timer playingTimer;

    /**
     * Starts a server for a new game.
     * @param viewType: cli or gui
     * @param socket_port: server's port to listen on
     * @param gameMode: single or multi player game online
     */
    public MultiEchoServer(String viewType, int socket_port, int gameMode, int start_time, int move_time) {
        this.viewType = viewType;
        this.socket_port = socket_port;
        this.move_time = move_time;
        this.start_time = start_time;
        this.gameMode = gameMode;

        this.playingTimer = new Timer();
        this.gameManager = new GameManager(gameMode, this);

        System.out.println(viewType  + "  " + socket_port +  "  " + move_time +  "  " + start_time +  "  " + gameMode);

        serverStart();
    }

    public static void main(String[] args) {

        int[] configs = ServerConfigParser.readServerConfig();

        if(args.length == 3) {
            if(args[2].equals("multiplayer")) {
                new MultiEchoServer(args[0], Integer.parseInt(args[1]), 2, configs[2], configs[3]);
            } else {
                new MultiEchoServer(args[0], Integer.parseInt(args[1]), 1, configs[2], configs[3]);
            }
        } else {
            defaultServerSetup(configs);
        }
    }

    private void serverStart() {
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            this.serverSocket = new ServerSocket(socket_port);
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server ready");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new EchoServerClientHandler(socket));
            } catch(IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

    private static void defaultServerSetup(int[] configs) {
        new MultiEchoServer("cli", configs[0], configs[1], configs[2], configs[3]);
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

}
