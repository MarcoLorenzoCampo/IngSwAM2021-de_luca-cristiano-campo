package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;

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

    private VirtualView playerVV;

    public SinglePlayerLobbyManager(IGame currentGame) {
        this.currentGame = currentGame;
        lorenzo = new LorenzoPlayer();
        numberOfTurns = 0;
    }

    /**
     * Checks if a player can be added.
     * @param nickname: name to add;
     */
    @Override
    public void addNewPlayer(String nickname, VirtualView virtualView) {
        if(player == null) {
            player = new RealPlayer(nickname);

            playerVV = virtualView;
            playerVV.showLoginOutput(true, true, false);

            setPlayingOrder();
        } else {

            playerVV.showLoginOutput(false, false, false);
        }
    }

    /**
     * Sets the only player as the first one. This will never change during the game.
     * Also call the method to deal LeaderCards.
     */
    @Override
    public void setPlayingOrder() {
        player.setFirstToPlay();

        playerVV.showGenericString("You'll move first, Lorenzo second.");

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

        playerVV.turnEnded("Lorenzo's turn now.");
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

    @Override
    public int getLobbySize() {
        return 1;
    }

    @Override
    public void reconnectPlayer(String nickname) {

    }

    @Override
    public void broadcastGenericMessage(String message) {

    }

    @Override
    public void broadCastWinMessage(String message) {

    }

    @Override
    public void broadCastMatchInfo() {

    }

    @Override
    public int turnOfPlayer(String current) {
        return 0;
    }

    public LorenzoPlayer getLorenzo() {
        return lorenzo;
    }

}
