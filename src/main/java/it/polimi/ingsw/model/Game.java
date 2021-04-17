package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.market.GameBoard;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * @author Marco Lorenzo Campo
 * Singleton class, only one game instance is available in each game.
 */
public class Game implements Serializable {

    private static final int MAX_CARDS_BOUGHT = 7;

    private static GameBoard gameBoard;
    private static Game gameInstance;
    private static GameState currentState;
    private Player currentPlayer;

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
    public static Game getGameInstance() {

        /* Create game instance if not present; */
        if (gameInstance == null) {
            gameInstance = new Game();
            gameBoard = GameBoard.getGameBoardInstance();
            currentState = new GameState();
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
