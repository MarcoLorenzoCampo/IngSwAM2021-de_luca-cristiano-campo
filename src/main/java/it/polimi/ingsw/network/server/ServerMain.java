package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.network.utilities.ServerConfigPOJO;
import it.polimi.ingsw.parsers.CommandLineParser;
import it.polimi.ingsw.parsers.ServerConfigParser;

/**
 * Accepted commands:
 *
 * user @ user:$ Class.java -port <port#> -<game_mode>
 *
 *
 * Follow this format to use specific settings. Defaults can be checked @ server_config.json
 *
 * <port#> : int from 1024 to Integer.GET_MAX;
 * <game_mode> : "singlePlayer" (1) or "multiPlayer" (2) (not case sensitive)
 *
 */
public class ServerMain {

    /**
     * Main method of the network.server package. Includes a small parsing using the {@link CommandLineParser}
     * @param args are given when launching the server jar.
     */
    public static void main(String[] args) {

        ServerConfigPOJO serverConfig;
        Server server;
        GameManager gameManager = null;
        SocketServer socketServer;

        if(!CommandLineParser.CmdValidator(args)) {
            serverConfig = defaultServerConfig();
        } else {
            serverConfig = customServerConfig(args);
        }

        if(serverConfig.getGameMode().equalsIgnoreCase("-singlePlayer")) {
            gameManager = new GameManager(1);
        }
        
        if(serverConfig.getGameMode().equalsIgnoreCase("-multiPlayer")){
            gameManager = new GameManager(2);
        }

        server = new Server(gameManager);
        socketServer = new SocketServer(server, serverConfig.getPort());

        Thread thread = new Thread(socketServer, "serverThread");
        thread.start();
    }

    /**
     * Helper method to set the default configurations if the user doesn't specify one.
     * @return the configurations in the JSON server_config.json.
     */
    private static ServerConfigPOJO defaultServerConfig() {
        return ServerConfigParser.readServerConfig();
    }

    /**
     * Helper method to parse main arguments.
     * @param args arguments to parse.
     * @return a parsed config file.
     */
    private static ServerConfigPOJO customServerConfig(String[] args) {
        return CommandLineParser.parseUserArgs(args);
    }
}
