package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.PlayerState;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<ResourceType> buffer;
    private HashMap<ResourceType, Integer> strongbox;
    private ArrayList<ResourceType> shelves;
    private ArrayList<ResourceType> extra_shelves_types;
    private List<LeaderCard> leaderCards;
    private String productionBoard;

    public LightweightModel() {
        this.playerStates = new ArrayList<>();
        productionBoard = new String();
    }

    public void addToPlayerState(String nickname) {
        
    }

    public void setLeaderCards(List<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public void setReducedResourceMarket(String reducedResourceMarket) {
        this.reducedResourceMarket = reducedResourceMarket;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
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

    public String getProductionBoard() {
        return productionBoard;
    }

    public String getPlayerStateByNickname(String nickname) {

        for(LightweightPlayerState playerState : playerStates) {
            if(playerState.getNickname().equals(nickname)) {
                return playerState.peekStatus();
            }
        }

        return null;
    }

    public void setBuffer(ArrayList<ResourceType> buffer) {
        this.buffer = buffer;
    }

    public void setStrongbox(HashMap<ResourceType, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void setWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras) {
        this.shelves = shelves;
        this.extra_shelves_types = extras;
    }

    public void setProductionBoard(String productions) {
        this.productionBoard = productions;
    }
}
