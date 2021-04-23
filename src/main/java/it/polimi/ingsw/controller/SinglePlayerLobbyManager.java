package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.player.RealPlayer;

public class SinglePlayerLobbyManager implements ILobbyManager {

    private final IGame currentGame;
    private RealPlayer player;
    private LorenzoPlayer lorenzo;

    public SinglePlayerLobbyManager(IGame currentGame) {
        this.currentGame = currentGame;
        lorenzo = new LorenzoPlayer();
    }

    @Override
    public void addNewPlayer(String nickname) throws NameTakenException, NoMorePlayersException {
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
        currentGame.setCurrentPlayer(player);
    }
}
