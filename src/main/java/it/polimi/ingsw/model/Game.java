package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.market.GameBoard;
import it.polimi.ingsw.model.player.Player;

import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * @author Marco Lorenzo Campo
 */
public class Game implements Serializable {

    private static final int MAX_CARDS_BOUGHT = 7;

    private static GameBoard gameBoard;
    private static Game gameInstance;
    private GameState currentState;
    private Player currentPlayer;

    /**
     * Initializes the main elements of a game: gameInstance,
     * gameBoard for all players and a empty player list to be filled.
     * @throws FileNotFoundException -- parsing error
     */
    public void init() throws FileNotFoundException {

        gameInstance = getGameInstance();
        gameBoard = GameBoard.getGameBoardInstance();
        currentState = new GameState();
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    /* getters */
    public GameBoard getGameBoard() {
        return gameBoard;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public int getMaxCardsBought() {
        return MAX_CARDS_BOUGHT;
    }
    public GameState getCurrentState() {
        return currentState;
    }
}
