package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;

public class BuyProductionCardAction implements Action{

    public PossibleAction getActionTag() {
        return actionTag;
    }

    private final PossibleAction actionTag = PossibleAction.BUY_PRODUCTION_CARD;

    @Override
    public void isValid() {
    }
}
