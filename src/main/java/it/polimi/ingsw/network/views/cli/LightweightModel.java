package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.PlayerState;
import it.polimi.ingsw.parsers.ProductionCardsParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A read only String version of the model, it gets updated whenever a change in the real model occurs. This can be
 * used as a quick to access storage of the model in case the player needs some information about the game
 * or other players.
 */
public class LightweightModel {

    private final List<ProductionCard> allProductions;
    private String reducedResourceMarket;
    private List<ProductionCard> AvailableCards;
    private final List<LightweightPlayerState> playerStates;
    private ArrayList<ResourceType> buffer;
    private HashMap<ResourceType, Integer> strongbox;
    private ArrayList<ResourceType> shelves;
    private ArrayList<ResourceType> extra_shelves_types;
    private List<LeaderCard> leaderCards;
    private HashMap<Integer, ProductionCard> productionBoard;

    public LightweightModel() {
        this.allProductions =  ProductionCardsParser.parseProductionDeck();
        this.playerStates = new ArrayList<>();
        productionBoard = new HashMap<>();
        productionBoard.put(0, null);
        productionBoard.put(1, null);
        productionBoard.put(2, null);
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

    public List<ProductionCard> getReducedAvailableCards() {
        return AvailableCards;
    }

    public void setReducedAvailableCards(List<ProductionCard> AvailableCards) {
        this.AvailableCards = AvailableCards;
    }

    public HashMap<Integer,ProductionCard> getProductionBoard() {
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

    public void setProductionBoard(HashMap<Integer, ProductionCard> productions) {
        this.productionBoard = productions;
    }
}
