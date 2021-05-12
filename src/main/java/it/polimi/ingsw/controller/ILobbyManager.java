package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.network.eventHandlers.VirtualView;

import java.util.List;

/**
 * Defines a generic LobbyManager's behavior.
 */
public interface ILobbyManager {

    void addNewPlayer(String nickname, VirtualView virtualView);
    void setPlayingOrder();
    void setNextTurn();
    void giveLeaderCards();
    List<RealPlayer> getRealPlayerList();
    int getNumberOfTurns();
    void reconnectPlayer(String nickname);
    void broadcastGenericMessage(String message);
    void broadCastWinMessage(String message);
    void broadCastMatchInfo();
}
