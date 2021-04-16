package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;

public class ActivateProductionsAction extends Action {

    public PossibleAction getActionTag() {
        return actionTag;
    }

    private final PossibleAction actionTag = PossibleAction.ACTIVATE_PRODUCTION;

    @Override
    public void isValid() {
    }
}
