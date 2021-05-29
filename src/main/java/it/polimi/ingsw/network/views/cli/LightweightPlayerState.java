package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Each player has references of other players, making it more accessible.
 */
public class LightweightPlayerState {

    private final String nickname;
    private Map<ResourceType, Integer> inventory;
    private int faithPosition;
    private List<EffectType> leaderCards;
    private HashMap<Integer, ProductionCard> productionBoard;

    public LightweightPlayerState(String nickname) {
        this.nickname = nickname;
        this.productionBoard = new HashMap<>();
        this.leaderCards = new ArrayList<>();
        this.faithPosition = 0;
        this.inventory = new HashMap<>();
    }

    public Map<ResourceType, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<ResourceType, Integer> inventory) {
        this.inventory = inventory;
    }

    public int getReducedFaithTrackInfo() {
        return faithPosition;
    }

    public void setReducedFaithTrackInfo(int faithPosition) {
        this.faithPosition = faithPosition;
    }

    public List<EffectType> getLeaderCards() {
        return leaderCards;
    }

    public void setLeaderCards(List<EffectType> leaderCards) {
        this.leaderCards = leaderCards;
    }

    public HashMap<Integer, ProductionCard> getProductionBoard() {
        return productionBoard;
    }

    public void setProductionBoard(HashMap<Integer, ProductionCard> productionBoard) {
        this.productionBoard = productionBoard;
    }

    public String getNickname() {
        return nickname;
    }
}
