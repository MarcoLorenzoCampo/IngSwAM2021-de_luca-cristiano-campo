package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.NicknameRequest;
import it.polimi.ingsw.network.messages.serverMessages.WinMessage;
import it.polimi.ingsw.network.utilities.ServerConfigPOJO;
import it.polimi.ingsw.parsers.CommandLineParser;
import it.polimi.ingsw.parsers.ServerConfigParser;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
            clientHandler.setNickname(nickname);
            gameManager.addVirtualView(nickname, virtualView);
            virtualView.showLoginOutput(true, true, false);
            virtualView.askPlayerNumber();

        }

        //first player already connected.
        else if (isSizeSet) {

            //First: check if it's a known name.
            if(findMatch(nickname) != null) {

                //Second: check if the player is disconnected.
                if(!Objects.requireNonNull(findMatch(nickname)).getPlayerState().isConnected()) {

                    //The player matched is currently offline, he can reconnect.
                    clientHandlerMap.put(nickname, clientHandler);
                    reconnectKnownPlayer(nickname, clientHandler, virtualView);

                } else {

                    //The player who has the same name is currently connected. Nickname rejected.
                    virtualView.showLoginOutput(true, false, false);
                    clientHandler.disconnect();
                }

            } else {

                //The nickname is not known. If the game hasn't started yet he can connect.
                if(!gameManager.isGameStarted()) {

                    clientHandlerMap.put(nickname, clientHandler);
                    gameManager.addVirtualView(nickname, virtualView);
                    onMessage(message);
                    clientHandler.setNickname(nickname);
                    virtualView.showLoginOutput(true, true, false);

                } else {

                    //Nickname not known but game started, connection can't happen.
                    virtualView.showGenericString("The game is started, you can't connect now!");
                    clientHandler.disconnect();
                }
            }

        } else {

            //Joining when the game is being set is forbidden.
            virtualView.showGenericString("Please rejoin later, the lobby is being set!" +
                    " You've been disconnected from the game.");
            clientHandler.disconnect();
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
     * finds the player whose name is matching.
     * @param nickname: nickname to match
     * @return: player reference.
     */
    private RealPlayer findMatch(String nickname) {

        for(RealPlayer realPlayer : gameManager.getLobbyManager().getRealPlayerList()) {
            if (realPlayer.getName().equals(nickname)) {
                return realPlayer;
            }
        }
        return null;
    }

    /**
     * Actions to perform when a player is disconnected from the game.
     * @param clientHandler: handler of the player who got disconnected.
     */
    public void onDisconnect(ClientHandler clientHandler) {

        String nicknameToRemove = clientHandler.getNickname();

        synchronized (lock) {

            //If the nickname is null, that means the player's setup wasn't done.
            if(nicknameToRemove != null) {
                gameManager.getLobbyManager().disconnectPlayer(nicknameToRemove);
                clientHandlerMap.remove(nicknameToRemove);

                //Last player online wins (when the game has started).
                if(clientHandlerMap.size() == 1) {

                    if(gameManager.isGameStarted()) {
                        gameManager.endGame(nicknameToRemove);
                        clientHandlerMap.clear();
                    }

                    //gets removed before dealing resources and leader cards.
                    if(!gameManager.isGameStarted()) {

                    }
                }

            } else {
                LOGGER.info(() -> "Removed a client before the login phase.");
            }
        }
    }

    /**
     * Method to reconnect the player if necessary.
     * @param nickname: name of the player.
     * @param clientHandler: handler of the player.
     */
    private void reconnectKnownPlayer(String nickname, ClientHandler clientHandler, VirtualView vv) {

        gameManager.getLobbyManager().reconnectPlayer(nickname);
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