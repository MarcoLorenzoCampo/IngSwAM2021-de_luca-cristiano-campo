package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;

public interface ILobbyManager {

    void addNewPlayer(String nickname) throws NameTakenException, NoMorePlayersException;
    void setPlayingOrder();
    void setNextTurn();
}
