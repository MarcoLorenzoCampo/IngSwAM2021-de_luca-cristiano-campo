package it.polimi.ingsw.model.market;


import java.io.FileNotFoundException;

public class GameBoard {

    ResourceMarket resourceMarket = new ResourceMarket();
    ProductionCardMarket productionCardMarket = new ProductionCardMarket();

    private static GameBoard gameBoardInstance = null;




    /**
     * @throws FileNotFoundException -- shown during the parsing phase
     *
     */
    private GameBoard() throws FileNotFoundException {}




    /**
     * @return gameBoardInstance: returns the single instance of the playerboard
     * created for a match
     */
    public static GameBoard getInstance() throws FileNotFoundException {

        /* Create gameboard instance if not present; */
        if (gameBoardInstance == null) {
            gameBoardInstance = new GameBoard();
        }
        return gameBoardInstance;
    }
}
