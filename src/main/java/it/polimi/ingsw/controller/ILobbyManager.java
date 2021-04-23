package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;

public interface ILobbyManager {

    void addNewPlayer(String nickname) throws NameTakenException, NoMorePlayersException;
    void setPlayingOrder();
    void setNextTurn();
}
