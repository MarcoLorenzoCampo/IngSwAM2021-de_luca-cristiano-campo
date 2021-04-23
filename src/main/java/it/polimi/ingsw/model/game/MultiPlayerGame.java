package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.market.GameBoard;
import it.polimi.ingsw.model.market.IGameBoard;
import it.polimi.ingsw.model.player.Player;

/**
 * @author Marco Lorenzo Campo
 * Singleton class, only one multiplayer game instance is available in each game.
 */
public class MultiPlayerGame extends Game implements IGame {

    private static final int MAX_CARDS_BOUGHT = 7;

    private IGameBoard iGameBoard;
    private static MultiPlayerGame multiPlayerGameInstance;
    private static GameState currentState;

    private MultiPlayerGame(IGameBoard iGameBoard) {
        this.iGameBoard = iGameBoard;
    }

    private MultiPlayerGame() { }

    public void setCurrentPlayer(Player currentPlayer) {
        super.setCurrentPlayer(currentPlayer);
    }

    /**
     * @throws EndGameException telling the controller game is ending next turn
     */
    public void endGame() throws EndGameException {
        super.endGame();
    }

    /**
     * @return multiPlayerGameInstance: returns the single instance of the game
     * created for a match
     */
    public static MultiPlayerGame getGameInstance() {

        /* Create game instance if not present; */
        if (multiPlayerGameInstance == null) {
            multiPlayerGameInstance = new MultiPlayerGame(GameBoard.getGameBoardInstance());
            currentState = new GameState();
        }
        return multiPlayerGameInstance;
    }

    public Player getCurrentPlayer() {
        return super.getCurrentPlayer();
    }

    public int getMaxCardsBought() {
        return MAX_CARDS_BOUGHT;
    }

    public GameState getCurrentState() {
        return super.getCurrentState();
    }

    public IGameBoard getIGameBoard() {
        return iGameBoard;
    }
}
