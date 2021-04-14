package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.BuyCardFromMarketException;
import it.polimi.ingsw.exceptions.GetResourceFromMarketException;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.model.actions.Action;

public abstract class PlayerBoard {

    public void getAction(Action performedAction) throws InvalidPlayerException, InvalidGameStateException,
            GetResourceFromMarketException, BuyCardFromMarketException {

        performedAction.isValid();
    }
}
