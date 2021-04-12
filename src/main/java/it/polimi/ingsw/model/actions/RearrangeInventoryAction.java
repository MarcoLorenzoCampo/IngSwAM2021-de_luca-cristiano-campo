package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;

public class RearrangeInventoryAction implements Action{

    private final PossibleAction actionTag = PossibleAction.REARRANGE_INVENTORY;

    public PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void isValid() {
    }
}
