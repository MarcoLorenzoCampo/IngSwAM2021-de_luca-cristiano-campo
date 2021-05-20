package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.network.views.cli.ColorCLI;

import java.util.HashMap;
import java.util.Map;

public class TokenDiscard implements IToken{

    private final Color color;
    private final ProductionCardMarket productionCardMarketReference;
    private ColorCLI colorCLI;
    private Map<Color, ColorCLI> cardColor = new HashMap<>();

    private final IGame game;

    public TokenDiscard(Color color, IGame game) {
        this.color = color;
        this.game = game;
        this.productionCardMarketReference = game.getGameBoard().getProductionCardMarket();
    }


    /**
     * Method to remove two cards of a specified color from the production
     * cards deck.
     */
    public void tokenAction(LorenzoPlayer lorenzo) {
        productionCardMarketReference.lorenzoRemoves(color);
        productionCardMarketReference.lorenzoRemoves(color);
    }

    @Override
    public String graphicalDraw() {
        initColorLevel();
        this.colorCLI = this.cardColor.get(color);
        return colorCLI.escape() + "-2 \u25AF";
    }

    private void initColorLevel(){
        cardColor.put(Color.GREEN, ColorCLI.ANSI_GREEN);
        cardColor.put(Color.BLUE, ColorCLI.ANSI_BLUE);
        cardColor.put(Color.YELLOW, ColorCLI.ANSI_BRIGHT_YELLOW);
        cardColor.put(Color.PURPLE, ColorCLI.ANSI_BRIGHT_PURPLE);
    }
}