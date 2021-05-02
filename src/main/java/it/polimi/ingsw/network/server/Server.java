package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Server {

    private final GameManager gameManager;
    private final Map<String, ClientHandler> clientHandlerMap;
    private ClientHandler currentClient;

    private final Object lock;

    private final ArrayList<String> disconnectedPlayers;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(GameManager gameManager) {

        this.gameManager = gameManager;
        gameManager.setServer(this);
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
        this.disconnectedPlayers = new ArrayList<>();
    }

    /**
     * Adding a new player to the game. Checks if the player is a known one, else
     * creates a new instance of the player.
     *
     * Note: No exception should be thrown here, it's an extra layer of validation to log
     * if something goes unexpectedly.
     *
     * @param nickname: name to be added
     * @param clientHandler: handler related to this client.
     */
    public void addNewClient(String nickname, ClientHandler clientHandler) {

        if(!isKnownPlayer(nickname)) {
            clientHandlerMap.put(nickname, clientHandler);
            clientHandler.setNickname(nickname);

            reconnectKnownPlayer(nickname, clientHandler);

        } else {

            try {
                gameManager.getLobbyManager().addNewPlayer(nickname);
            } catch (NameTakenException e) {
                Server.LOGGER.severe("Nickname management error!");
            } catch (NoMorePlayersException e) {
                Server.LOGGER.severe("Player handling error!");
            }
        }
    }

    /**
     * Forward the message to the controller to be handled accordingly.
     * @param message: message to be verified and executed.
     */
    public void onMessage(Message message) {
        gameManager.getMessageHandler().onMessage(message);
    }

    public void onDisconnection(ClientHandler clientHandler) {

        String offlineClient = clientHandler.getNickname();

        disconnectedPlayers.add(offlineClient);

        if(offlineClient.equalsIgnoreCase(gameManager.getCurrentGame().getCurrentPlayer().getName())) {
            gameManager.getLobbyManager().setNextTurn();

            LOGGER.info(() -> offlineClient + " left the game, next player alerted.");
        }

        clientHandlerMap.get(offlineClient).disconnect();

        LOGGER.info(() -> "Removed " + offlineClient + " from the client list.");
    }

    /**
     * Checks if the player who is trying to connect has already been registered
     * @param nickname: potential known player's name.
     * @return: boolean value of the outcome.
     */
    private boolean isKnownPlayer(String nickname) {
        return (disconnectedPlayers != null && disconnectedPlayers.contains(nickname));
    }

    private void reconnectKnownPlayer(String nickname, ClientHandler clientHandler) {

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

    public Map<String, ClientHandler> getClientHandlerMap() {
        return clientHandlerMap;
    }

}
