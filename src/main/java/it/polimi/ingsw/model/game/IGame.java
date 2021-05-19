package it.polimi.ingsw.model.game;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.market.GameBoard;
import it.polimi.ingsw.model.player.RealPlayer;

public interface IGame {

    void setCurrentPlayer(RealPlayer currentRealPlayer);
    RealPlayer getCurrentPlayer();
    void endGame() throws EndGameException;
    GameState getCurrentState();
    GameBoard getGameBoard();
    void setCurrentState(PossibleGameStates setupSize);
}
