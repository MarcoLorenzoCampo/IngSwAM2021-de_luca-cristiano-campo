package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LobbyManager {

    private final int MAX_PLAYERS = 4;

    private List<Player> playerList;

    public LobbyManager() {
        playerList = new LinkedList<>();
    }

    public void addNewPlayer(String nickname) throws NameTakenException, NoMorePlayersException {
        if(playerList.size() == MAX_PLAYERS)
            throw new NoMorePlayersException("Exceeded max number of players!");

        for(Player player : playerList) {
            if(player.getName().equals(nickname))
                throw new NameTakenException(nickname);
        }
        playerList.add(new Player(nickname));
    }

    public void setPlayingOrder() {

        Collections.shuffle(playerList);
        playerList.get(0).setFirstToPlay();
        Game.getGameInstance().setCurrentPlayer(playerList.get(0));
    }
}
