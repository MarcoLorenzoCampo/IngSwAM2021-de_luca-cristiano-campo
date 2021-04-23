package it.polimi.ingsw.model.game;

import it.polimi.ingsw.enumerations.PossibleGameStates;

public class GameState {

    private PossibleGameStates gameState;
    private boolean singlePlayer = false;

    public GameState() {
        gameState = PossibleGameStates.SETUP;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public void setSinglePlayer() {
        this.singlePlayer = true;
    }

    public PossibleGameStates getGameState() {
        return gameState;
    }
    public void setGameState(PossibleGameStates gameState) {
        this.gameState = gameState;
    }
}
