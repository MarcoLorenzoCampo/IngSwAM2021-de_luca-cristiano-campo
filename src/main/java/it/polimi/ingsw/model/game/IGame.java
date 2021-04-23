package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.market.IGameBoard;
import it.polimi.ingsw.model.player.Player;

public interface IGame {

    void setCurrentPlayer(Player currentPlayer);
    Player getCurrentPlayer();
    void endGame() throws EndGameException;
    GameState getCurrentState();
    IGameBoard getIGameBoard();
}
