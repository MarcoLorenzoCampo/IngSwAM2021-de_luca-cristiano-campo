package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.market.ProductionCardMarket;

public class TokenDiscard implements IToken{

    private final Color color;
    private final ProductionCardMarket productionCardMarketReference;

    public TokenDiscard(Color color) {
        this.color = color;
        this.productionCardMarketReference = Game.getGameInstance().getGameBoard().getProductionCardMarket();
    }

    /**
     * Method to remove two cards of a specified color from the production
     * cards deck.
     */
    @Override
    public void tokenAction() {
        productionCardMarketReference.lorenzoRemovesTwo(color);
    }
}