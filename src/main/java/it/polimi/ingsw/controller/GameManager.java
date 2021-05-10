package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.network.eventHandlers.observers.Observer;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.Server;

import java.io.Serializable;

/**
 * Class to manage the entire playing game. It has instances of the currentGame, LobbyManager,
 * ActionManager.
 */
public final class GameManager implements Observer, Serializable {

    private static final long serialVersionUID = -5547896474378477025L;
    private Server server;
    private final IGame currentGame;
    private final ActionManager actionManager;
    private ILobbyManager lobbyManager;
    private final MessageHandler messageHandler;

    /**
     * Constructor of the game manager.
     */
    public GameManager() {

        currentGame = PlayingGame.getGameInstance();
        actionManager = new ActionManager(currentGame, this);
        messageHandler = new MessageHandler();
    }

    /**
     * Lobby manager is set later in the game when a lobby size message is sent.
     * @param gameMode : decides whether the LobbyManager will be for a single player game or multiplayer.
     *              1 = single player game;
     *              2 = multiplayer game;
     */
    public void setLobbyManager(int gameMode) {
        if(gameMode == 1) {
            lobbyManager = new SinglePlayerLobbyManager(currentGame);
        }
        if(gameMode == 2) {
            lobbyManager = new MultiPlayerLobbyManager(this);
        }
    }

    public void broadCastMessage(String message) {

    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public IGame getCurrentGame() {
        return currentGame;
    }

    public ILobbyManager getLobbyManager() {
        return lobbyManager;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }


    /**
     * Method to end the game. Broadcasts the outcome of the match and
     * @param message: end game message
     */
    public void endGame(String message) {
        broadCastMessage(message);
        //computes scores and such to show
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }


    @Override
    public void update(Message message) {

    }
}
