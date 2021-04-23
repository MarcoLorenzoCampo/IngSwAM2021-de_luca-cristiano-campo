package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;

import java.util.List;

public class RealPlayerBoard extends PlayerBoard {

    private int boughtCardsNumber;
    private final String owner;
    private final FaithTrack faithTrack;
    List<LeaderCard> ownedLeaderCards;
    ProductionBoard productionBoard;
    InventoryManager inventoryManager;

    public RealPlayerBoard(String owner) {
        this.owner = owner;
        faithTrack = new FaithTrack();
        boughtCardsNumber = 0;
        productionBoard = new ProductionBoard();
        inventoryManager = new InventoryManager();
    }

    public void setOwnedLeaderCards(List<LeaderCard> ownedLeaderCards) {
        this.ownedLeaderCards = ownedLeaderCards;
    }

    @Override
    public void getAction(Action performedAction) throws InvalidPlayerException,
            InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, EndTurnException,
            NoMatchingRequisitesException, LeaderCardException, EndGameException, InvalidProductionSlotException {
        super.getAction(performedAction);
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
    public List<LeaderCard> getOwnedLeaderCards() {
        return ownedLeaderCards;
    }
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
