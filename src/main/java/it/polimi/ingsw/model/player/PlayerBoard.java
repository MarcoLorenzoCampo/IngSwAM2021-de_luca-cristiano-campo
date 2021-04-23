package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.actions.Action;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;

import java.io.FileNotFoundException;

public abstract class PlayerBoard {

    public void getAction(Action performedAction) throws InvalidPlayerException, InvalidGameStateException,
            GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException,
            EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException {

        performedAction.isValid();
    }

    public abstract FaithTrack getFaithTrack();

    public abstract ProductionBoard getProductionBoard();
}
