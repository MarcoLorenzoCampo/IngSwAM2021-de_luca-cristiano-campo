package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class SinglePlayerLobbyManager implements ILobbyManager {

    int numberOfTurns;
    private final IGame currentGame;
    private RealPlayer player;
    private final LorenzoPlayer lorenzo;

    public SinglePlayerLobbyManager(IGame currentGame) {
        this.currentGame = currentGame;
        lorenzo = new LorenzoPlayer();
        numberOfTurns = 0;
    }

    @Override
    public void addNewPlayer(String nickname) throws NoMorePlayersException {

        if(player == null) {
            player = new RealPlayer(nickname);
        } else  {
            throw new NoMorePlayersException("SinglePlayer games have only one player!");
        }
        setPlayingOrder();
    }

    @Override
    public void setPlayingOrder() {
        player.setFirstToPlay();
        currentGame.setCurrentPlayer(player);

        giveLeaderCards();
    }

    @Override
    public void setNextTurn() {
        numberOfTurns++;
        lorenzo.getLorenzoPlayerBoard().getAction(new LorenzoAction(lorenzo));
    }

    @Override
    public void giveLeaderCards() {
        List<LeaderCard> leaderCards = LeaderCardsDeckBuilder.deckBuilder();

        player.setOwnedLeaderCards(
                leaderCards
                        .stream()
                        .limit(4)
                        .collect(Collectors.toList()));
    }

    @Override
    public List<RealPlayer> getRealPlayerList() {
        return null;
    }

    @Override
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public LorenzoPlayer getLorenzo() {
        return lorenzo;
    }

}
