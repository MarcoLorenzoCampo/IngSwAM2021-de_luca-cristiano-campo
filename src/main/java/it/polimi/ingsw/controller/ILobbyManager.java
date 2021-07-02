package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.eventHandlers.VirtualView;

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
    int getLobbySize();
    void reconnectPlayer(String nickname, VirtualView vv);
    void broadcastGenericMessage(String message);
    void broadCastWinMessage(String message);
    int turnOfPlayer(String current);
    void disconnectPlayer(String nicknameToDisconnect);
    void setObserver(String nickname, VirtualView vv);
    void showStartingUpdates();
    void randomizedResourcesSetup(String disconnectedNickname);
    void randomizedLeadersSetup(String disconnectedNickname);
    void forwardPlayerUpdates();
}
