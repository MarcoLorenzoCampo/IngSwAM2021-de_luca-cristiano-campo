package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;

/**
 * Class to manage the entire playing game. It has instances of the currentGame, LobbyManager,
 * ActionManager.
 */
public final class GameManager {


    private final IGame currentGame;
    private final ActionManager actionManager;
    private ILobbyManager lobbyManager;

    /**
     * Constructor of the game manager.
     * @param gameMode : decides whether the LobbyManager will be for a single player game or multiplayer.
     *                 1 = single player game;
     *                 2 = multiplayer game;
     */
    public GameManager(int gameMode) {

        currentGame = PlayingGame.getGameInstance();
        actionManager = new ActionManager(currentGame, this.lobbyManager);

        if(gameMode == 1) {
            lobbyManager = new SinglePlayerLobbyManager(currentGame);
        }
        if(gameMode == 2) {
            lobbyManager = new MultiPlayerLobbyManager(currentGame);
        }
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
}
