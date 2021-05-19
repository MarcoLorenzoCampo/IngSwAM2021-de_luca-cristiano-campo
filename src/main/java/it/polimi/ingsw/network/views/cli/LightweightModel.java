package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.model.player.PlayerState;

import java.util.ArrayList;
import java.util.List;

/**
 * A read only String version of the model, it gets updated whenever a change in the real model occurs. This can be
 * used as a quick to access storage of the model in case the player needs some information about the game
 * or other players.
 */
public class LightweightModel {

    private String reducedResourceMarket;
    private String reducedAvailableCards;
    private final List<LightweightPlayerState> playerStates;

    public LightweightModel() {
        this.playerStates = new ArrayList<>();
    }

    public void addToPlayerState(String nickname) {
        
    }

    public void setReducedResourceMarket(String reducedResourceMarket) {
        this.reducedResourceMarket = reducedResourceMarket;
    }

    public String getReducedResourceMarket() {
        return reducedResourceMarket;
    }

    public String getReducedAvailableCards() {
        return reducedAvailableCards;
    }

    public void setReducedAvailableCards(String reducedAvailableCards) {
        this.reducedAvailableCards = reducedAvailableCards;
    }

    public String getPlayerStateByNickname(String nickname) {

        for(LightweightPlayerState playerState : playerStates) {
            if(playerState.getNickname().equals(nickname)) {
                return playerState.peekStatus();
            }
        }

        return null;
    }
}
