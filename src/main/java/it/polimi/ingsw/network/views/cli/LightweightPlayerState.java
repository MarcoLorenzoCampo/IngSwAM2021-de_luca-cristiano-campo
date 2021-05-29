package it.polimi.ingsw.network.views.cli;

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
    private String reducedFaithTrackInfo;
    private List<LeaderCard> leaderCards;
    private HashMap<Integer, ProductionCard> productionBoard;

    public LightweightPlayerState(String nickname) {
        this.nickname = nickname;
        this.productionBoard = new HashMap<>();
        this.leaderCards = new ArrayList<>();
        this.reducedFaithTrackInfo = "";
        this.inventory = new HashMap<>();
    }

    public Map<ResourceType, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<ResourceType, Integer> inventory) {
        this.inventory = inventory;
    }

    public String getReducedFaithTrackInfo() {
        return reducedFaithTrackInfo;
    }

    public void setReducedFaithTrackInfo(String reducedFaithTrackInfo) {
        this.reducedFaithTrackInfo = reducedFaithTrackInfo;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public void setLeaderCards(List<LeaderCard> leaderCards) {
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
