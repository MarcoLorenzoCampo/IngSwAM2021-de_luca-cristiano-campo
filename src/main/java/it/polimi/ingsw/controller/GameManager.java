package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.network.server.Server;

/**
 * Class to manage the entire playing game. It has instances of the currentGame, LobbyManager,
 * ActionManager.
 */
public final class GameManager {

    private Server server;
    private final IGame currentGame;
    private final ActionManager actionManager;
    private ILobbyManager lobbyManager;
    private final MessageHandler messageHandler;

    /**
     * Constructor of the game manager.
     * @param gameMode : decides whether the LobbyManager will be for a single player game or multiplayer.
     *                 1 = single player game;
     *                 2 = multiplayer game;
     */
    public GameManager(int gameMode) {

        currentGame = PlayingGame.getGameInstance();
        actionManager = new ActionManager(currentGame, this);
        messageHandler = new MessageHandler();

        if(gameMode == 1) {
            lobbyManager = new SinglePlayerLobbyManager(currentGame);
        }
        if(gameMode == 2) {
            lobbyManager = new MultiPlayerLobbyManager(this);
        }
    }

    public void broadCastMessage(String text) {

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


}
