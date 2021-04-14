package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.market.GameBoard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

/**
 * @author Marco Lorenzo Campo
 */
public class Game implements Serializable {

    private static final int MAX_PLAYERS = 4;
    private static final int MAX_CARDS_BOUGHT = 7;

    private static GameBoard gameBoard;
    private LinkedList<Player> playerList;
    private static Game gameInstance;
    private GameState currentState;
    private Player currentPlayer;
    private List<LeaderCard> leadersDeck;

    /**
     * Initializes the main elements of a game: gameInstance,
     * gameBoard for all players and a empty player list to be filled.
     * @throws FileNotFoundException -- parsing error
     */
    public void init() throws FileNotFoundException {

        gameInstance = getGameInstance();
        gameBoard = GameBoard.getGameBoardInstance();
        playerList = new LinkedList<>();
        currentState = new GameState();
        leadersDeck = LeaderCardsDeckBuilder.deckBuilder();
    }

    /**
     * @throws EndGameException telling the controller game is ending next turn
     */
    public void endGame() throws EndGameException {
        throw new EndGameException("Game ended! " + currentPlayer.getName() + " won!");
    }

    private Game() { }

    /**
     * @return gameInstance: returns the single instance of the game
     * created for a match
     */
    public static Game getGameInstance()  {

        /* Create game instance if not present; */
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    public void addPlayer(String newName) throws NameTakenException, NoMorePlayersException {
        checkNewName(newName);
        playerList.add(new Player(newName));
        playerList.getLast().getPlayerBoard().setOwnedLeaderCards(
                leadersDeck.subList((playerList.size()-1)*4, playerList.size()*4-1));
    }

    /**
     * @param newName -- potential name to add
     * @throws NameTakenException -- indicates newName is
     * already taken
     */
    private void checkNewName(String newName) throws NameTakenException, NoMorePlayersException {
        if(playerList.size() == MAX_PLAYERS)
            throw new NoMorePlayersException("Exceeded max number of players!");

        for(Player player : playerList) {
            if(player.getName().equals(newName))
                throw new NameTakenException(newName);
        }
    }

    private void setPlayingOrder() {
        Collections.shuffle(playerList);
        playerList.getFirst().setFirstToPlay();
        currentPlayer = playerList.getFirst();
    }

    /* getters */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public LinkedList<Player> getPlayerList() {
        return playerList;
    }

    public int getMaxCardsBought() {
        return MAX_CARDS_BOUGHT;
    }

    public GameState getCurrentState() {
        return currentState;
    }

}
