package it.polimi.ingsw.model.game;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.market.GameBoard;
import it.polimi.ingsw.model.player.RealPlayer;

/**
 * Singleton class, only one multiplayer game instance is available in each game.
 */
public class PlayingGame implements IGame {

    private static final int MAX_CARDS_BOUGHT = 7;

    private final GameBoard gameBoard;
    private static PlayingGame playingGameInstance;
    private static GameState currentState;
    private RealPlayer currentPlayer;

    private PlayingGame(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setCurrentPlayer(RealPlayer currentRealPlayer) {
        this.currentPlayer = currentRealPlayer;
    }

    /**
     * @throws EndGameException telling the controller game is ending next turn
     */
    public void endGame() throws EndGameException {
        throw new EndGameException("PlayingGame ended! " + currentPlayer.getName() + " won!");
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

    /**
     * Method to reset the controller references when a game is ended.
     */
    public static void terminate() {
        playingGameInstance = null;
    }

    public RealPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public int getMaxCardsBought() {
        return MAX_CARDS_BOUGHT;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public void setCurrentState(PossibleGameStates newState) {
        PlayingGame.currentState.setGameState(newState);
    }
}
