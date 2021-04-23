package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;

public final class GameManager {

    IGame currentGame;
    ActionManager actionManager;
    ILobbyManager lobbyManager;

    public GameManager(boolean isSinglePlayer) {

        currentGame = PlayingGame.getGameInstance();
        actionManager = new ActionManager(currentGame, this.lobbyManager);

        if(isSinglePlayer) {
            lobbyManager = new SinglePlayerLobbyManager(currentGame);
        }
        if(!isSinglePlayer) {
            lobbyManager = new MultiPlayerLobbyManager(currentGame);
        }
    }

    public ActionManager getActionManager() {
        return actionManager;
    }
}
