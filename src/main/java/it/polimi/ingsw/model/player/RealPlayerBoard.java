package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.actions.Action;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import java.util.LinkedList;
import java.util.List;

public class RealPlayerBoard extends PlayerBoard {

    private int boughCardsNumber;
    private String owner;
    private FaithTrack faithTrack;
    List<LeaderCard> ownedLeaderCards;

    public RealPlayerBoard(String owner) {
        this.owner = owner;
        faithTrack = new FaithTrack();
        boughCardsNumber = 0;
    }

    public void setOwnedLeaderCards(List<LeaderCard> ownedLeaderCards) {
        this.ownedLeaderCards = ownedLeaderCards;
    }

    @Override
    public void getAction(Action performedAction) throws InvalidPlayerException,
            InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException {
        super.getAction(performedAction);
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
}
