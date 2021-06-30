package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Represents the activation of one of the productions granted by an ExtraProductionLeader,
 * it holds the slot in which the card is stored and the output wanted
 */
public class ActivateExtraProductionAction extends Action {
    private final PossibleAction actionTag = PossibleAction.ACTIVATE_PRODUCTION;
    private final int slot;
    private final ResourceType output;



    public ActivateExtraProductionAction(String actionSender, int slot, ResourceType output) {
        super.setActionSender(actionSender);
        this.slot = slot;
        this.output = output;

    }

    public ResourceType getOutput() {
        return output;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
