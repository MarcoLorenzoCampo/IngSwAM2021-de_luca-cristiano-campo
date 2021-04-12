package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.EndGameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.utilities.PlayerBoard;

public class RealPlayerBoard extends PlayerBoard {

    private int boughCardsNumber;
    private String owner;
    private FaithTrack faithTrack;
    private ProductionSet productionSet;
    //List<LeaderCards> ownedLeaderCards;

    public RealPlayerBoard(String owner) {
        this.owner = owner;
        productionSet = new ProductionSet();
        faithTrack = new FaithTrack();
        //ownedLeaderCards = new LinkedList<>();
        boughCardsNumber = 0;
    }

    public void increaseBoughCardsCount() throws EndGameException {
        boughCardsNumber++;
        if(boughCardsNumber == Game.getGameInstance().getMaxCardsBought())
            Game.getGameInstance().endGame();
    }

    public String getOwner() {
        return owner;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public ProductionSet getProductionSet() {
        return productionSet;
    }
}
