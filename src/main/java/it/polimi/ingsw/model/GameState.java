package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.PossibleGameStates;

public class GameState {
    private static PossibleGameStates gameState;

    public GameState() {
        gameState = PossibleGameStates.SETUP;
    }

    public static void setGameState(PossibleGameStates gameState) {
        GameState.gameState = gameState;
    }
}
