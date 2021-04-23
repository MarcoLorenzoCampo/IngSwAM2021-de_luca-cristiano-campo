package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.player.RealPlayer;

/**
 * Interface injection to replace the use of a Singleton.
 */
public abstract class Game implements IGame {

    private RealPlayer currentRealPlayer;
    private GameState currentState;

    public void setCurrentPlayer(RealPlayer currentRealPlayer) {
        this.currentRealPlayer = currentRealPlayer;
    }

    public RealPlayer getCurrentPlayer() {
        return currentRealPlayer;
    }

    public void endGame() throws EndGameException {
        throw new EndGameException("PlayingGame ended! " + currentRealPlayer.getName() + " won!");
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
