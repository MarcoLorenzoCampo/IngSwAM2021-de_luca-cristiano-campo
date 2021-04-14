package it.polimi.ingsw.model.market;


import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;

import java.io.FileNotFoundException;
import java.util.List;


public class GameBoard {

    private final ResourceMarket resourceMarket;
    private final ProductionCardMarket productionCardMarket;
    private static GameBoard gameBoardInstance = null;

    /**
     * @throws FileNotFoundException -- shown during the parsing phase
     */
    private GameBoard() throws FileNotFoundException {
        resourceMarket = new ResourceMarket();
        productionCardMarket = new ProductionCardMarket();
    }

    /**
     * @return gameBoardInstance: returns the single instance of the playerboard
     * created for a match
     */
    public static GameBoard getGameBoardInstance() throws FileNotFoundException {
        /* Create gameboard instance if not present; */
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
