package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.player.LorenzoPlayer;

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
    public void tokenAction(LorenzoPlayer lorenzo) {
        productionCardMarketReference.lorenzoRemoves(color);
        productionCardMarketReference.lorenzoRemoves(color);
    }
}