package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Allows the player to swap shelves, single resources can't be swapped.
 */
public class RearrangeInventoryAction extends Action{

    private final PossibleAction actionTag = PossibleAction.REARRANGE_INVENTORY;

    public PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void isValid() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
