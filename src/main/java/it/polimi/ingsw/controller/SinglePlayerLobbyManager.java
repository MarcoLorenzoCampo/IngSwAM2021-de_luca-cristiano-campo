package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.player.RealPlayer;

public class SinglePlayerLobbyManager implements ILobbyManager {

    private final IGame currentGame;
    private RealPlayer player;
    private final LorenzoPlayer lorenzo;

    public SinglePlayerLobbyManager(IGame currentGame) {
        this.currentGame = currentGame;
        lorenzo = new LorenzoPlayer();
    }

    @Override
    public void addNewPlayer(String nickname) {
        player = new RealPlayer(nickname);

        setPlayingOrder();
    }

    @Override
    public void setPlayingOrder() {
        player.setFirstToPlay();
        currentGame.setCurrentPlayer(player);
    }

    @Override
    public void setNextTurn() {
        lorenzo.getLorenzoPlayerBoard().getAction(new LorenzoAction(lorenzo));
    }
}
