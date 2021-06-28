package it.polimi.ingsw.model.game;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.model.market.GameBoard;
import it.polimi.ingsw.model.player.RealPlayer;

public interface IGame {

    void setCurrentPlayer(RealPlayer currentRealPlayer);
    RealPlayer getCurrentPlayer();
    GameState getCurrentState();
    GameBoard getGameBoard();
    void setCurrentState(PossibleGameStates setupSize);
}
