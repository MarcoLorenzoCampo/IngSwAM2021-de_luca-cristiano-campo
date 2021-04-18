package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class to manage the players and the sequence of turns.
 */
public final class LobbyManager {

    private static final int MAX_PLAYERS = 4;
    private static int numberOfTurns = 0;

    private static final List<Player> playerList = new LinkedList<>();

    private LobbyManager() {
        throw new UnsupportedOperationException("Utility class must not be initiated!");
    }

    /**
     * Adds a new player if the validation steps are verified.
     * @param nickname: The name chosen by the player.
     * @throws NameTakenException: Name is already in use.
     * @throws NoMorePlayersException: there are 4 players already.
     */
    public static void addNewPlayer(String nickname) throws NameTakenException, NoMorePlayersException {
        if(playerList.size() == MAX_PLAYERS)
            throw new NoMorePlayersException("Exceeded max number of players!");

        for(Player player : playerList) {
            if(player.getName().equals(nickname))
                throw new NameTakenException(nickname);
        }
        playerList.add(new Player(nickname));
    }

    public static void setPlayingOrder() {

        Collections.shuffle(playerList);
        playerList.get(0).setFirstToPlay();
        Game.getGameInstance().setCurrentPlayer(playerList.get(0));
    }

    public static void setNextTurn() {
        numberOfTurns++;
        Game.getGameInstance()
                .setCurrentPlayer(playerList.get(numberOfTurns%playerList.size()));
    }
}
