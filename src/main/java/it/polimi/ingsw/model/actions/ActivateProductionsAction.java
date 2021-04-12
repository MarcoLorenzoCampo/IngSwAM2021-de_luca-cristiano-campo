package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;

public class ActivateProductionsAction implements Action {

    public PossibleAction getActionTag() {
        return actionTag;
    }

    private final PossibleAction actionTag = PossibleAction.ACTIVATE_PRODUCTION;

    @Override
    public void isValid() {

    }
}
