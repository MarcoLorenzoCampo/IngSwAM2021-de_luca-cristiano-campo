package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.player.RealPlayer;

import java.util.List;

/**
 * Defines a generic LobbyManager's behavior.
 */
public interface ILobbyManager {

    void addNewPlayer(String nickname) throws NameTakenException, NoMorePlayersException;
    void setPlayingOrder();
    void setNextTurn();
    void giveLeaderCards();
    List<RealPlayer> getRealPlayerList();
    int getNumberOfTurns();
}
