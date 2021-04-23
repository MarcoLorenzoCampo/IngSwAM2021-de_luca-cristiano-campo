package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.ProductionCardMarket;

public class TokenDiscard implements IToken{

    private final Color color;
    private final ProductionCardMarket productionCardMarketReference;

    private final IGame game;

    public TokenDiscard(Color color, IGame game) {
        this.color = color;
        this.game = game;
        this.productionCardMarketReference = game.getIGameBoard().getProductionCardMarket();
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