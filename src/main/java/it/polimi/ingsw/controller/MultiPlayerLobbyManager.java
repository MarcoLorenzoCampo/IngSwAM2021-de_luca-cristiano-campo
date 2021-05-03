package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to manage players and turns in a multiplayer game.
 */
public final class MultiPlayerLobbyManager implements ILobbyManager {

    private static final int MAX_PLAYERS = 4;

    private final GameManager gameManager;
    private int numberOfTurns;
    private final List<RealPlayer> realPlayerList;

    public MultiPlayerLobbyManager(IGame currentGame, GameManager gameManager) {
        this.realPlayerList = new LinkedList<>();
        numberOfTurns = 0;
        this.gameManager = gameManager;
    }

    /**
     * Adds a new player if the validation steps are verified.
     * @param nickname: The name chosen by the player.
     * @throws NameTakenException: Name is already in use.
     * @throws NoMorePlayersException: there are 4 players already.
     */
    @Override
    public void addNewPlayer(String nickname) throws NameTakenException, NoMorePlayersException {
        if(realPlayerList.size() == MAX_PLAYERS)
            throw new NoMorePlayersException("Exceeded max number of players!");

        for(RealPlayer realPlayer : realPlayerList) {
            if(realPlayer.getName().equals(nickname))
                throw new NameTakenException(nickname);
        }
        realPlayerList.add(new RealPlayer(nickname));
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
     */
    @Override
    public void setNextTurn() {
        numberOfTurns++;

        while(!gameManager.getServer().getClientHandlerMap()
                .get(realPlayerList.get(numberOfTurns% realPlayerList.size()).getName()).isConnected()) {

            numberOfTurns++;
        }

        PlayingGame.getGameInstance()
                .setCurrentPlayer(realPlayerList.get(numberOfTurns% realPlayerList.size()));

        gameManager.getServer().setCurrentClient(PlayingGame.getGameInstance().getCurrentPlayer().getName());
    }

    /**
     * Method to deal 4 leader cards for each player.
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
                    break;
                case 2:
                    PlayingGame.getGameInstance()
                            .getCurrentState()
                            .setGameState(PossibleGameStates.WAIT_FOR_INPUT);

                    realPlayerList.get(i)
                            .getPlayerBoard()
                            .getFaithTrack()
                            .increaseFaithMarker();
                    break;

                case 3:
                    PlayingGame.getGameInstance()
                            .getCurrentState()
                            .setGameState(PossibleGameStates.WAIT_FOR_INPUT);

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
