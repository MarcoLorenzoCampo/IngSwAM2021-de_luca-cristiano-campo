package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.market.ProductionCardMarket;

public class TokenDiscard implements IToken{
    private Color color;
    private ProductionCardMarket productionCardMarket;

    /**
     * this method removes the cards from the productioncardmarket
     */
    @Override
    public void tokenAction() {

    }
}