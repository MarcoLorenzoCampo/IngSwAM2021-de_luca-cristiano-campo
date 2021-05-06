package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;
import it.polimi.ingsw.network.views.VirtualView;

import java.util.*;

/**
 * Class to manage players and turns in a multiplayer game.
 */
public final class MultiPlayerLobbyManager implements ILobbyManager {

    /**
     * Hardcoded. Default value and can't be changed.
     */
    private static final int MAX_PLAYERS = 4;

    /**
     * Lobby dimension set by the first client to connect.
     */
    private int lobbySize;

    /**
     * reference to the game manager.
     */
    private final GameManager gameManager;

    /**
     * Integer to track the progression of the turns and keep track of the playing order.
     * auxIndex is used to store temporary positions in the player list.
     */
    private int numberOfTurns, auxIndex;

    /**
     * List of all player, both connected and disconnected.
     */
    private final List<RealPlayer> realPlayerList;

    /**
     * Map to store tuples: (Nickname, VirtualView).
     */
    private final Map<String, VirtualView> viewsByNickname;

    public MultiPlayerLobbyManager(GameManager gameManager) {
        this.realPlayerList = new LinkedList<>();
        numberOfTurns = 0;
        auxIndex = 0;
        this.gameManager = gameManager;
        viewsByNickname= new HashMap<>();
    }

    /**
     * Adds a new player if the validation steps are verified.
     * @param nickname: The name chosen by the player.
     */
    @Override
    public void addNewPlayer(String nickname, VirtualView virtualView) {
        if(viewsByNickname.isEmpty()) {

            viewsByNickname.put(nickname, virtualView);
            realPlayerList.add(new RealPlayer(nickname));
            virtualView.showLoginOutput(true, true, false);

            virtualView.askPlayerNumber();
        }

        if(realPlayerList.size() == MAX_PLAYERS) {
            virtualView.showError("The lobby is full, sorry!");
            return;
        }

        for(RealPlayer realPlayer : realPlayerList) {
            if(realPlayer.getName().equals(nickname)) {
                virtualView.showLoginOutput(true, false, false);
                return;
            }
        }
    }

    /**
     * Sets the boolean value of the connection to true.
     * @param nickname: name associated to the player;
     */
    @Override
    public void reconnectPlayer(String nickname) {

        for(RealPlayer realPlayer : realPlayerList) {
            if(realPlayer.getName().equals(nickname)) {
                realPlayer.getPlayerState().connect();
            }
        }
    }

    public void setLobbySize(int lobbySize, VirtualView virtualView) {
        if(lobbySize > MAX_PLAYERS) {
            virtualView.showError("Too many players!");
            virtualView.askPlayerNumber();
        } else {
            this.lobbySize = lobbySize;
        }
    }

    /**
     * Method to set the playing order once the game starts. It's invoked once per game.
     */
    @Override
    public void setPlayingOrder() {

        Collections.shuffle(realPlayerList);
        realPlayerList.get(0).setFirstToPlay();
        PlayingGame.getGameInstance().setCurrentPlayer(realPlayerList.get(0));

        giveLeaderCards();
        setDefaultResources();
    }

    /**
     * Iterates over the handler's list in the server, looking for the next player. The iteration goes on
     * as long as it finds a connected host to set as the next current player.
     *
     * Clients aren't removed from the player list since they might reconnect later. No progress they made
     * will be lost this way.
     */
    @Override
    public void setNextTurn() {

        numberOfTurns++;
        auxIndex ++;

        int newCurrentIndex = auxIndex % realPlayerList.size();

        if (realPlayerList.get(newCurrentIndex).getPlayerState().isConnected()) {

            while (realPlayerList.get(newCurrentIndex).getPlayerState().isConnected()) {

                if (newCurrentIndex == realPlayerList.size() - 1) {
                    newCurrentIndex = 0;
                }
            }
        }

        auxIndex = newCurrentIndex;

        gameManager.getCurrentGame().setCurrentPlayer(realPlayerList.get(newCurrentIndex));

        gameManager.getServer()
                .setCurrentClient(PlayingGame.getGameInstance().getCurrentPlayer().getName());
    }

    /**
     * Method to deal 4 leader cards {@link LeaderCard} for each player.
     */
    @Override
    public void giveLeaderCards() {
        List<LeaderCard> leaderCards = LeaderCardsDeckBuilder.deckBuilder();

        for(int i=0; i<realPlayerList.size(); i++) {
            realPlayerList
                    .get(i)
                    .setOwnedLeaderCards(leaderCards.subList(4*i, 4*i + 4));
        }
    }

    /**
     * When the game starts, each player is given a specific number of resources and
     * faith track points.
     */
    private void setDefaultResources() {
        for(int i = 0; i< realPlayerList.size(); i++) {

            switch(i) {
                case 1:
                    PlayingGame.getGameInstance()
                            .getCurrentState()
                            .setGameState(PossibleGameStates.WAIT_FOR_INPUT);

                    viewsByNickname.get(realPlayerList.get(i).getName()).askSetupResource();
                    break;
                case 2:
                    PlayingGame.getGameInstance()
                            .getCurrentState()
                            .setGameState(PossibleGameStates.WAIT_FOR_INPUT);

                    viewsByNickname.get(realPlayerList.get(i).getName()).askSetupResource();

                    realPlayerList.get(i)
                            .getPlayerBoard()
                            .getFaithTrack()
                            .increaseFaithMarker();
                    break;

                case 3:
                    PlayingGame.getGameInstance()
                            .getCurrentState()
                            .setGameState(PossibleGameStates.WAIT_FOR_INPUT);

                    viewsByNickname.get(realPlayerList.get(i).getName()).askSetupResource();
                    viewsByNickname.get(realPlayerList.get(i).getName()).askSetupResource();

                    realPlayerList.get(i)
                            .getPlayerBoard()
                            .getFaithTrack()
                            .increaseFaithMarker();

                    realPlayerList.get(i)
                            .getPlayerBoard()
                            .getFaithTrack()
                            .increaseFaithMarker();
                    break;
            }
        }
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public List<RealPlayer> getRealPlayerList() {
        return realPlayerList;
    }
}
