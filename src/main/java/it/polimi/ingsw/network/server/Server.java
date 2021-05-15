package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.NicknameRequest;
import it.polimi.ingsw.network.utilities.ServerConfigPOJO;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.parsers.CommandLineParser;
import it.polimi.ingsw.parsers.ServerConfigParser;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Accepted commands:
 *
 * user @ user:$ Class.java -port <port#>
 *
 *
 * Follow this format to use specific settings. Defaults can be checked @ server_config.json
 *
 * <port#> : int from 1024 to Integer.GET_MAX;
 *
 */
public class Server implements Serializable {

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final long serialVersionUID = -6336401959495770721L;

    /**
     * Reference to the controller.
     */
    private final GameManager gameManager;

    /**
     * A Map of the clients are currently playing. Set using the pair <Nickname, ClientHandler>
     */
    private final Map<String, ClientHandler> clientHandlerMap;

    /**
     * Reference to the current player (handler in this case) given by the
     * controller when the next turn is set.
     */
    private ClientHandler currentClient;

    private final Object lock;

    /**
     * Auxiliary boolean to know when lobby size is set
     */
    private boolean isSizeSet;


    /**
     * Default constructor. Creates empty instances of the collections needed and sets up
     * the controller.
     * @param gameManager: class given by the user input.
     */
    public Server(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameManager.setServer(this);
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
        this.isSizeSet = false;
    }

    /**
     * method to call when lobbyManager is available
     */
    public void sizeHasBeenSet() {
        isSizeSet = true;
    }

    /**
     * Adding a new player to the game. Checks if the player is a known one, else
     * creates a new instance of the player.
     *
     * @param message: message with name to be added
     * @param clientHandler: handler related to this client.
     */
    public void addNewClient(NicknameRequest message, ClientHandler clientHandler) {

        VirtualView virtualView = new VirtualView(clientHandler);
        String nickname = message.getSenderUsername();

        //first player logged in
        if(clientHandlerMap.size() == 0) {

            clientHandlerMap.put(nickname, clientHandler);
            onMessage(message);
            gameManager.addVirtualView(nickname, virtualView);
            virtualView.showLoginOutput(true, true, false);
            virtualView.askPlayerNumber();
        }

        else if(isSizeSet) {
            if (!isKnownPlayer(nickname) && clientHandlerMap.size() != 0) {
                gameManager.addVirtualView(nickname, virtualView);
                onMessage(message);
                gameManager.addVirtualView(nickname, virtualView);
            }

            //If it's a known player
            else if (isKnownPlayer(nickname)) {

                clientHandlerMap.put(nickname, clientHandler);
                clientHandler.setNickname(nickname);

                reconnectKnownPlayer(nickname, clientHandler, virtualView);
            }
        }
        else{
            //wait a while, we are setting the game for you :)
        }
    }

    /**
     * Forward the message to the controller to be handled accordingly.
     * @param message: message to be verified and executed.
     */
    public void onMessage(Message message) {
        gameManager.onMessage(message);
    }

    /**
     * Actions to perform when a player is disconnected from the game.
     * @param clientHandler: handler of the player who got disconnected.
     */
    public void onDisconnect(ClientHandler clientHandler) {

        synchronized (lock) {

            if(clientHandler.getNickname() != null) {

                String offlineClient = clientHandler.getNickname();
                clientHandlerMap.remove(offlineClient);

                if (offlineClient.equals(currentClient.getNickname())) {

                    gameManager.getLobbyManager().setNextTurn();

                    LOGGER.info(() -> offlineClient + " left the game, players alerted.");
                }

                gameManager.getLobbyManager().broadcastGenericMessage(offlineClient + " disconnected.");
                LOGGER.info(() -> "Removed " + offlineClient + " from the client list.");

                //Also add && gameState = isPlaying.
                if (clientHandlerMap.size() == 1) {
                    gameManager.endGame("You're the last player online. You won!");


                }
            }

            if(clientHandler.getNickname() == null){
                LOGGER.info(() -> "Removed a client before the login phase.");
            }
        }
    }

    /**
     * Checks if the player who is trying to connect has already been registered in the controller.
     * @param nickname: potential known player's name.
     * @return: boolean value of the outcome.
     */
    private boolean isKnownPlayer(String nickname) {
        return gameManager.getLobbyManager()
                .getRealPlayerList()
                .stream()
                .anyMatch(p -> p.getName().equals(nickname));
    }

    /**
     * Method to reconnect the player if necessary.
     * @param nickname: name of the player.
     * @param clientHandler: handler of the player.
     */
    private void reconnectKnownPlayer(String nickname, ClientHandler clientHandler, VirtualView vv) {

        clientHandlerMap.put(nickname, clientHandler);

        gameManager.getLobbyManager().reconnectPlayer(nickname);
    }

    /**
     * Set a reference for the current player in the server.
     * @param nickname: current player.
     */
    public void setCurrentClient(String nickname) {
        currentClient = clientHandlerMap.get(nickname);
    }

    /**
     * Get current client.
     * @return: current client.
     */
    public ClientHandler getCurrentClient() {
        return currentClient;
    }

    /**
     * Get a map containing handlers associated with the respective player nickname.
     */
    public Map<String, ClientHandler> getClientHandlerMap() {
        return clientHandlerMap;
    }

    //------------------------------------- MAIN METHOD ----------------------------------------------------------

    /**
     * Main method of the network.server package. Includes a small parsing using the {@link CommandLineParser}
     * @param args are given when launching the server jar.
     */
    public static void main(String[] args) {

        ServerConfigPOJO serverConfig;

        Server server;

        GameManager gameManager = new GameManager();

        SocketServer socketServer;

        if(!CommandLineParser.CmdValidator(args)) {
            serverConfig = defaultServerConfig();
        } else {
            serverConfig = customServerConfig(args);
        }

        server = new Server(gameManager);
        socketServer = new SocketServer(server, serverConfig.getPort());

        Thread serverSocketThread = new Thread(socketServer, "serverThread");
        serverSocketThread.start();

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