package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.parsers.ProductionCardsParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A read only String version of the model, it gets updated whenever a change in the real model occurs. This can be
 * used as a quick to access storage of the model in case the player needs some information about the game
 * or other players.
 */
public class LightweightModel {

    private List<ProductionCard> allProductions;
    private String reducedResourceMarket;
    private List<ProductionCard> availableCards;
    private List<LightweightPlayerState> playerStates;
    private Map<ResourceType, Integer> strongbox;
    private ArrayList<ResourceType> shelves;
    private ArrayList<ResourceType> extra_shelves_types;
    private List<LeaderCard> leaderCards;
    private HashMap<Integer, ProductionCard> productionBoard;
    private FaithTrack faithTrack;

    public LightweightModel() {
        this.faithTrack = new FaithTrack();
        this.allProductions = ProductionCardsParser.parseProductionDeck();
        this.playerStates = new ArrayList<>();
        this.productionBoard = new HashMap<>();
        this.shelves = new ArrayList<>();
        this.strongbox = new HashMap<>();
        this.availableCards = new ArrayList<>();
        this.extra_shelves_types = new ArrayList<>();
        this.leaderCards = new ArrayList<>();

        productionBoard.put(0, null);
        productionBoard.put(1, null);
        productionBoard.put(2, null);
    }

    public void addToPlayerState(String nickname) {
        
    }

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public List<LightweightPlayerState> getPlayerStates() {
        return playerStates;
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

    public List<ProductionCard> getReducedAvailableCards() {
        return availableCards;
    }

    public void setReducedAvailableCards(List<ProductionCard> AvailableCards) {
        this.availableCards = AvailableCards;
    }

    public HashMap<Integer,ProductionCard> getProductionBoard() {
        return productionBoard;
    }

    public Map<ResourceType, Integer> getStrongbox() {
        return strongbox;
    }

    public ArrayList<ResourceType> getShelves() {
        return shelves;
    }

    public ArrayList<ResourceType> getExtra_shelves_types() {
        return extra_shelves_types;
    }

    public void setStrongbox(Map<ResourceType, Integer> strongbox) {
        this.strongbox = strongbox;
    }

    public void setWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras) {
        this.shelves = shelves;
        this.extra_shelves_types = extras;
    }

    public LightweightPlayerState getPlayerStateByName(String nickname) {
        for(LightweightPlayerState l : playerStates) {
            if(l.getNickname().equals(nickname)) {
                return l;
            }
        }
        return null;
    }

    public void setProductionBoard(HashMap<Integer, ProductionCard> productions) {
        this.productionBoard = productions;
    }
}
