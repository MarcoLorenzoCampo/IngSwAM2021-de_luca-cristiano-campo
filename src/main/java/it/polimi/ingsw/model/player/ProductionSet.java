package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.utilities.BaseProduction;

import java.util.LinkedList;
import java.util.List;

public class ProductionSet {

    private List<ProductionCard> ownedCards;
    private BaseProduction baseProduction;
    private List<ProductionCard> availableProductions;

    public ProductionSet() {
        ownedCards = new LinkedList<>();
        baseProduction = new BaseProduction();
        availableProductions = null;
    }

    public List<ProductionCard> getOwnedCards() {
        return ownedCards;
    }

    public void getAvailableProductions() {

    }
}
