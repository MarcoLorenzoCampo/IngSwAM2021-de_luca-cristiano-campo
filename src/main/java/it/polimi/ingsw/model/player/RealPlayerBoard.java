package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.Bought7CardsMessage;
import it.polimi.ingsw.network.messages.serverMessages.EndGameMessage;

/**
 * Main board of the player. Contains all the references to play the game.
 */
public class RealPlayerBoard extends Observable {

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
    private final ProductionBoard productionBoard;

    /**
     * Reference to the inventory manager.
     */
    private final InventoryManager inventoryManager;

    public RealPlayerBoard(String owner) {
        this.owner = owner;
        faithTrack = new FaithTrack();
        boughtCardsNumber = 0;
        productionBoard = new ProductionBoard();
        inventoryManager = new InventoryManager();
    }

    /**
     * Method to increase the number of bought cards, when the max number gets reached then
     * the observers gets notified and the end game procedure is started.
     */
    public void increaseBoughCardsCount() {
        boughtCardsNumber++;
        if(boughtCardsNumber == PlayingGame.getGameInstance().getMaxCardsBought()) {
            notifyControllerObserver(new Bought7CardsMessage());
        }
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