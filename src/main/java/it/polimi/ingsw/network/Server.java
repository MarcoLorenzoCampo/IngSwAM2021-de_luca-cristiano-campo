package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.parsers.ServerConfigParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Locale;
import java.util.Timer;

/**
 * Setting up the server based on the input given by command line:
 * Accepted command formats:
 *
 * 3 arguments:
 *      user@user:~$ java -jar client.jar <"cli"/"gui"> <port_number> <game_mode>
 *
 * 2 arguments:
 *      user@user:~$ java -jar client.jar <"cli"/"gui"> <game_mode>
 *
 * 1 argument:
 *      user@user:~$ java -jar client.jar <"cli"/"gui">
 *
 *
 * For non-specified options, a Json config file will be used. Every non recognized input will stop the
 * setup process and a default config file will be used instead.
 * (Other parameters can be edited from the config file as well)
 *
 *
 * Accepted commands (not case sensitive):
 * <"cli"/"gui"> -> "cli" or "gui".
 * <port_number> -> integer from 1024 to Integer.MAX_VALUE.
 * <game_mode> -> "online_single_player" or "online_multiplayer".
 */

public class Server {

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
    public Server(String viewType, int socket_port, int gameMode, int start_time, int move_time) throws IOException {
        this.viewType = viewType;
        this.socket_port = socket_port;
        this.move_time = move_time;
        this.start_time = start_time;
        this.gameMode = gameMode;

        this.playingTimer = new Timer();
        this.gameManager = new GameManager(gameMode);

        //System.out.println(viewType  + "  " + socket_port +  "  " + move_time +  "  " + start_time +  "  " + gameMode);

        serverInit();
    }

    public static void main(String[] args) throws IOException {

        int[] configs = ServerConfigParser.readServerConfig();

        switch(args.length) {

            /* first cmd line parameter only, check if he typed "cli" or "gui" */
            case(1):
                if(args[0].equals("cli") || args[0].equals("gui")) {

                    new Server(args[0], configs[0], configs[1], configs[2], configs[3]);
                    break;
                }
                defaultServerSetup(configs);
                break;

            /* two cmd line parameters: check if the first is "cli" or "gui", check if the port number is usable */
            case(2):
                if((args[0].equalsIgnoreCase("cli") || args[0].equalsIgnoreCase("gui")) &&
                        (Integer.parseInt(args[1].toLowerCase()) > 1024 && Integer.parseInt(args[1].toLowerCase()) < Integer.MAX_VALUE)) {

                    new Server(args[0], Integer.parseInt(args[1]), configs[1], configs[2], configs[3]);
                    break;
                }
                defaultServerSetup(configs);
                break;

            /* every parameter is in use: check if the first is "cli" or "gui", if the port is usable and if the
               gameMode chosen is acceptable */
            case(3):
                if((args[0].equalsIgnoreCase("cli") || args[0].equalsIgnoreCase("gui")) &&
                        (Integer.parseInt(args[1].toLowerCase()) > 1024 && Integer.parseInt(args[1].toLowerCase(Locale.ROOT)) < Integer.MAX_VALUE) &&
                        (Integer.parseInt(args[2].toLowerCase()) == Integer.parseInt("online_single_player")
                                || Integer.parseInt(args[2].toLowerCase()) == Integer.parseInt("online_multiplayer"))) {

                    new Server(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), configs[2], configs[3]);
                    defaultServerSetup(configs);
                    break;
                }
                defaultServerSetup(configs);
                break;

            /* cmd line format isn't accepted, loading defaults configuration */
            default:
                defaultServerSetup(configs);
        }
    }

    private void serverInit() throws IOException {
        this.serverSocket = new ServerSocket(socket_port);
        System.out.println("Connection started");
    }

    private static void defaultServerSetup(int[] configs) throws IOException {
        new Server("cli", configs[0], configs[1], configs[2], configs[3]);
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
