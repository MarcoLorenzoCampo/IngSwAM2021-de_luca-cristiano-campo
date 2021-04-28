package it.polimi.ingsw.network;

import it.polimi.ingsw.parsers.ServerConfigParser;

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
 * For non specified options, a Json config file will be used. Every non recognized format will stop the
 * reading process and use the config file. Other parameters can be edited from the config file as well.
 *
 *
 * Accepted commands (case insensitive):
 * <"cli"/"gui"> -> "cli" or "gui".
 * <port_number> -> integer from 1024 to Integer.MAX_VALUE.
 * <game_mode> -> "local_single_player", "online_single_player" or "online_multiplayer".
 *
 */
public class Server {

    private final String configPath = "src/main/resources/server_config.json";

    private final String viewType;
    private final int socket_port;
    private final int gameMode;

    private final int start_time;
    private final int move_time;

    private final Timer playingTimer;

    public Server(String viewType, int socket_port, int gameMode, int start_time, int move_time) {
        this.viewType = viewType;
        this.socket_port = socket_port;
        this.move_time = move_time;
        this.start_time = start_time;
        this.gameMode = gameMode;
        playingTimer = new Timer();
    }

    public static void main(String[] args) {

        int[] configs = ServerConfigParser.readServerConfig();

        switch(args.length) {

            case(1):
                if(args[0].equals("cli") || args[0].equals("gui")) {

                    new Server(args[0], configs[0], configs[1], configs[2], configs[3]);
                    break;
                }
                defaultServerSetup(configs);
                break;

            case(2):
                if((args[0].equals("cli") || args[0].equals("gui")) &&
                        (Integer.parseInt(args[1]) > 1024 && Integer.parseInt(args[1]) < Integer.MAX_VALUE)) {

                    new Server(args[0], Integer.parseInt(args[1]), configs[1], configs[2], configs[3]);
                    break;
                }
                defaultServerSetup(configs);
                break;

            case(3):
                if((args[0].equals("cli") || args[0].equals("gui")) &&
                        (Integer.parseInt(args[1]) > 1024 && Integer.parseInt(args[1]) < Integer.MAX_VALUE) &&
                        (Integer.parseInt(args[2]) == Integer.parseInt("local_single_player")
                                || Integer.parseInt(args[2]) == Integer.parseInt("online_single_player")
                                || Integer.parseInt(args[2]) == Integer.parseInt("online_multiplayer"))) {

                    new Server(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), configs[2], configs[3]);
                    defaultServerSetup(configs);
                    break;
                }
                defaultServerSetup(configs);
                break;

            default:
                defaultServerSetup(configs);
        }
    }

    private static void defaultServerSetup(int[] configs) {
        new Server("cli", configs[1], configs[2], configs[3], configs[4]);
    }


    public String getViewType() {

        return viewType;
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
