package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

public class RealPlayerBoard extends PlayerBoard {

    String owner;
    FaithTrack faithTrack;
    List<ProductionCard> ownedProductionCards;
    //List<LeaderCards> ownedLeaderCards;
    //InventoryManager inventoryManager;

    public RealPlayerBoard(String owner) {
        this.owner = owner;
        ownedProductionCards = new LinkedList<>();
        faithTrack = new FaithTrack();
        //ownedLeaderCards = new LinkedList<>();
        //inventoryManager = new InventoryManager();
    }

    public void increaseBoughCardsCount() throws EndGameException {
        if(ownedProductionCards.size() == Game.getGameInstance().getMaxCardsBought())
            Game.getGameInstance().endGame();
    }








}
