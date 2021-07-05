package it.polimi.ingsw.model.market;

/**
 * Class to represent all the available elements of the market such as the Resource Market and
 * the Production Cards Market.
 * {@link ResourceMarket}
 * {@link ProductionCardMarket}
 */
public class GameBoard {

    /**
     * Resource Market.
     */
    private final ResourceMarket resourceMarket;

    /**
     * Production Cards Market.
     */
    private final ProductionCardMarket productionCardMarket;

    /**
     * Null instance for singleton use.
     */
    private static GameBoard gameBoardInstance = null;
    
    private GameBoard() {
        resourceMarket = new ResourceMarket();
        productionCardMarket = new ProductionCardMarket();
    }

    /**
     * @return gameBoardInstance: returns the single instance of the playerboard
     * created for a match
     */
    public static GameBoard getGameBoardInstance() {
        /* Create game board instance if not present; */
        if (gameBoardInstance == null) {
            gameBoardInstance = new GameBoard();
        }
        return gameBoardInstance;
    }



    public ResourceMarket getResourceMarket() {
        return resourceMarket;
    }

    public ProductionCardMarket getProductionCardMarket() {
        return productionCardMarket;
    }
}
