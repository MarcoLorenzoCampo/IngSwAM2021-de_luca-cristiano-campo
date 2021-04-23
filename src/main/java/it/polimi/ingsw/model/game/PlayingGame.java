package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.market.GameBoard;
import it.polimi.ingsw.model.market.IGameBoard;
import it.polimi.ingsw.model.player.RealPlayer;

/**
 * @author Marco Lorenzo Campo
 * Singleton class, only one multiplayer game instance is available in each game.
 */
public class PlayingGame extends Game implements IGame {

    private static final int MAX_CARDS_BOUGHT = 7;

    private IGameBoard iGameBoard;
    private static PlayingGame playingGameInstance;
    private static GameState currentState;

    private PlayingGame(IGameBoard iGameBoard) {
        this.iGameBoard = iGameBoard;
    }

    private PlayingGame() { }

    public void setCurrentPlayer(RealPlayer currentRealPlayer) {
        super.setCurrentPlayer(currentRealPlayer);
    }

    /**
     * @throws EndGameException telling the controller game is ending next turn
     */
    public void endGame() throws EndGameException {
        super.endGame();
    }

    /**
     * @return playingGameInstance: returns the single instance of the game
     * created for a match
     */
    public static PlayingGame getGameInstance() {

        /* Create game instance if not present; */
        if (playingGameInstance == null) {
            playingGameInstance = new PlayingGame(GameBoard.getGameBoardInstance());
            currentState = new GameState();
        }
        return playingGameInstance;
    }

    public RealPlayer getCurrentPlayer() {
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
