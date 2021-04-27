package it.polimi.ingsw.model.market;

/**
 * Interface injection to replace the use of a Singleton.
 */
public class GameBoard implements IGameBoard {

    private final ResourceMarket resourceMarket;
    private final ProductionCardMarket productionCardMarket;
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
