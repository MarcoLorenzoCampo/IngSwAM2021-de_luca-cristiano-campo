package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.PossibleGameStates;

public class GameState {

    private PossibleGameStates gameState;

    public PossibleGameStates getGameState() {
        return gameState;
    }

    public GameState() {
        gameState = PossibleGameStates.SETUP;
    }

    public void setGameState(PossibleGameStates gameState) {
        this.gameState = gameState;
    }
}
