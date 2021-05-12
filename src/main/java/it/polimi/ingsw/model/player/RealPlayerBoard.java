package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;

import java.io.Serializable;
import java.util.List;

/**
 * Main board of the player. Contains all the references to play the game.
 */
public class RealPlayerBoard {

    /**
     * Number of cards bought.
     */
    private int boughtCardsNumber;

    /**
     * Name of the player who owns this board.
     */
    private final String owner;

    /**
     * Reference to the player's faith track.
     */
    private final FaithTrack faithTrack;

    /**
     * Reference to the production cards bought.
     */
    ProductionBoard productionBoard;

    /**
     * Reference to the inventory manager.
     */
    InventoryManager inventoryManager;

    public RealPlayerBoard(String owner) {
        this.owner = owner;
        faithTrack = new FaithTrack();
        boughtCardsNumber = 0;
        productionBoard = new ProductionBoard();
        inventoryManager = new InventoryManager();
    }

    /**
     * Main method called to run a specific action that has to be validated before being executed.
     * @param actionToPerform: action to be run. {@link Action}
     */
    public void getAction(Action actionToPerform) throws InvalidPlayerException,
            InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, EndTurnException,
            NoMatchingRequisitesException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {

        actionToPerform.isValid();
    }

    public void increaseBoughCardsCount() throws EndGameException {
        boughtCardsNumber++;
        if(boughtCardsNumber == PlayingGame.getGameInstance().getMaxCardsBought())
            PlayingGame.getGameInstance().endGame();
    }

    public String getOwner() {
        return owner;
    }
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }
    public ProductionBoard getProductionBoard() {
        return productionBoard;
    }
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
