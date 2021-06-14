package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.player.LorenzoPlayer;
import it.polimi.ingsw.model.utilities.Reducible;
import it.polimi.ingsw.network.views.cli.ColorCLI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TokenDiscard extends AbstractToken {

    private final Color color;
    private final ProductionCardMarket cardMarket;
    private ColorCLI colorCLI;
    private Map<Color, ColorCLI> cardColor = new HashMap<>();

    private final IGame game;

    public TokenDiscard(Color color, IGame game) {
        this.color = color;
        this.game = game;
        this.cardMarket = game.getGameBoard().getProductionCardMarket();
    }

    /**
     * Method to remove two cards of a specified color from the production
     * cards deck.
     */
    public void tokenAction(LorenzoPlayer lorenzo) {
        cardMarket.lorenzoRemoves(color);
        cardMarket.lorenzoRemoves(color);
    }

    @Override
    public String graphicalDraw() {
        initColorLevel();
        this.colorCLI = this.cardColor.get(color);
        return colorCLI.escape() + "-2 \u25AF";
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getQuantity() {
        return 2;
    }

    private void initColorLevel(){
        cardColor.put(Color.GREEN, ColorCLI.ANSI_GREEN);
        cardColor.put(Color.BLUE, ColorCLI.ANSI_BLUE);
        cardColor.put(Color.YELLOW, ColorCLI.ANSI_BRIGHT_YELLOW);
        cardColor.put(Color.PURPLE, ColorCLI.ANSI_BRIGHT_PURPLE);
    }

    @Override
    public String toString() {
        return "\n----------------------------------------------\n" + "" +
                "Lorenzo played a 'Discard 2 Cards' Token:" +
            "\nColor discarded: " + color
                + "\n----------------------------------------------\n";
    }
}