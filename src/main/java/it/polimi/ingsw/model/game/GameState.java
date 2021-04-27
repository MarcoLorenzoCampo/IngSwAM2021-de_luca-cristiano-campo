package it.polimi.ingsw.model.game;

import it.polimi.ingsw.enumerations.PossibleGameStates;

public class GameState {

    private PossibleGameStates gameState;

    public GameState() {
        gameState = PossibleGameStates.SETUP;
    }

    public PossibleGameStates getGameState() {
        return gameState;
    }

    public void setGameState(PossibleGameStates gameState) {
        this.gameState = gameState;
    }
}
