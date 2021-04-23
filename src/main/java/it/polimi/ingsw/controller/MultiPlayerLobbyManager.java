package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.player.RealPlayer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class to manage the players and the sequence of turns.
 */
public final class MultiPlayerLobbyManager implements ILobbyManager {

    private static final int MAX_PLAYERS = 4;

    private final IGame currentGame;

    private int numberOfTurns;
    private final List<RealPlayer> realPlayerList;

    public MultiPlayerLobbyManager(IGame currentGame) {
        realPlayerList = new LinkedList<>();
        numberOfTurns = 0;
        this.currentGame = currentGame;
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

    @Override
    public void setPlayingOrder() {
        Collections.shuffle(realPlayerList);
        realPlayerList.get(0).setFirstToPlay();
        PlayingGame.getGameInstance().setCurrentPlayer(realPlayerList.get(0));

        setDefaultResources();
    }

    @Override
    public void setNextTurn() {
        numberOfTurns++;
        PlayingGame.getGameInstance()
                .setCurrentPlayer(realPlayerList.get(numberOfTurns% realPlayerList.size()));
    }

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
}
