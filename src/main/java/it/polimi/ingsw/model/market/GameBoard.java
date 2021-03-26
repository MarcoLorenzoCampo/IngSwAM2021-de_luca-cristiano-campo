package it.polimi.ingsw.model.market;

public class GameBoard {

    ResourceMarket resourceMarket;
    ProductionCardMarket productionCardMarket;
    private static GameBoard instance = null;

    private GameBoard() {}

    public static GameBoard getInstance() {

        // Create gameboard instance if not present;
        if (instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    public GameBoard(ResourceMarket resourceMarket, ProductionCardMarket productionCardMarket) {
        this.resourceMarket = resourceMarket;
        this.productionCardMarket = productionCardMarket;
    }
}
