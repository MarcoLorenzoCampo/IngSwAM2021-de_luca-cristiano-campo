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

/**
 * Class to manage players and turns in a single player game.
 */
public class SinglePlayerLobbyManager implements ILobbyManager {

    private int numberOfTurns;
    private final IGame currentGame;
    private RealPlayer player = null;
    private final LorenzoPlayer lorenzo;

    public SinglePlayerLobbyManager(IGame currentGame) {
        this.currentGame = currentGame;
        lorenzo = new LorenzoPlayer();
        numberOfTurns = 0;
    }

    /**
     * Checks if a player can be added.
     * @param nickname: name to add;
     * @throws NoMorePlayersException thrown if a "add player" command is mistakenly sent after the first one.
     */
    @Override
    public void addNewPlayer(String nickname) throws NoMorePlayersException {
        if(player == null) {
            player = new RealPlayer(nickname);
        } else  {
            throw new NoMorePlayersException("SinglePlayer games have only one player!");
        }
        setPlayingOrder();
    }

    /**
     * Sets the only player as the first one. This will never change during the game.
     * Also call the method to deal LeaderCards.
     */
    @Override
    public void setPlayingOrder() {
        player.setFirstToPlay();
        currentGame.setCurrentPlayer(player);

        giveLeaderCards();
    }

    /**
     * After each turn, a new {@link LorenzoAction} is generated and run.
     * Lorenzo is modeled as a player but his actions are generated automatically by the controller.
     */
    @Override
    public void setNextTurn() {
        numberOfTurns++;
        lorenzo.getLorenzoPlayerBoard().getAction(new LorenzoAction(lorenzo));
    }

    /**
     * Method to deal leader cards. The only player receives the first 4.
     */
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
