package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.player.Player;

/**
 * Interface injection to replace the use of a Singleton.
 */
public abstract class Game implements IGame {

    private Player currentPlayer;
    private GameState currentState;

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void endGame() throws EndGameException {
        throw new EndGameException("MultiPlayerGame ended! " + currentPlayer.getName() + " won!");
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
