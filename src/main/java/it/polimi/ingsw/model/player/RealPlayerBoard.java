package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;

import java.util.List;

public class RealPlayerBoard extends PlayerBoard {

    private int boughtCardsNumber;
    private final String owner;
    private final FaithTrack faithTrack;
    List<LeaderCard> ownedLeaderCards;

    public RealPlayerBoard(String owner) {
        this.owner = owner;
        faithTrack = new FaithTrack();
        boughtCardsNumber = 0;
    }

    public void setOwnedLeaderCards(List<LeaderCard> ownedLeaderCards) {
        this.ownedLeaderCards = ownedLeaderCards;
    }

    @Override
    public void getAction(Action performedAction) throws InvalidPlayerException,
            InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, EndTurnException,
            NoMatchingRequisitesException, LeaderCardException {
        super.getAction(performedAction);
    }

    public void increaseBoughCardsCount() throws EndGameException {
        boughtCardsNumber++;
        if(boughtCardsNumber == Game.getGameInstance().getMaxCardsBought())
            Game.getGameInstance().endGame();
    }

    public String getOwner() {
        return owner;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }


    public List<LeaderCard> getOwnedLeaderCards() {
        return ownedLeaderCards;
    }
}
