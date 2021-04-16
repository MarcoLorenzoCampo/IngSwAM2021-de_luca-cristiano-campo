package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.actions.Action;

public abstract class PlayerBoard {

    public void getAction(Action performedAction) throws InvalidPlayerException, InvalidGameStateException,
            GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException {

        performedAction.isValid();
    }
}
