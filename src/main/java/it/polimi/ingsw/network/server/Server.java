package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.virtualView.VirtualView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Server {

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

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
     * Default constructor. Creates empty instances of the collections needed and sets up
     * the controller.
     * @param gameManager: class given by the user input.
     */
    public Server(GameManager gameManager) {
        this.gameManager = gameManager;
        gameManager.setServer(this);
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
    }

    /**
     * Adding a new player to the game. Checks if the player is a known one, else
     * creates a new instance of the player.
     *
     * @param nickname: name to be added
     * @param clientHandler: handler related to this client.
     */
    public void addNewClient(String nickname, ClientHandler clientHandler) {

        if(!isKnownPlayer(nickname)) {
            clientHandlerMap.put(nickname, clientHandler);
            clientHandler.setNickname(nickname);

            VirtualView virtualView = new VirtualView(clientHandler);
            reconnectKnownPlayer(nickname, clientHandler, virtualView);

        } else {
            VirtualView virtualView = new VirtualView(clientHandler);
            gameManager.getLobbyManager().addNewPlayer(nickname, virtualView);
        }
    }

    /**
     * Forward the message to the controller to be handled accordingly.
     * @param message: message to be verified and executed.
     */
    public void onMessage(Message message) {
        gameManager.getMessageHandler().onMessage(message);
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

                gameManager.broadCastMessage(offlineClient + " disconnected.");
                LOGGER.info(() -> "Removed " + offlineClient + " from the client list.");

                if (clientHandlerMap.size() == 1) {
                    gameManager.endGame("You're the last player online. I guess you won!");

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
}