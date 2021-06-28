package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

public class ActivateExtraProductionAction extends Action {
    private final PossibleAction actionTag = PossibleAction.ACTIVATE_PRODUCTION;
    private final int slot;
    private final ResourceType output;

    private final IGame game;

    public ActivateExtraProductionAction(String actionSender, int slot, ResourceType output, IGame game) {
        super.setActionSender(actionSender);
        this.slot = slot;
        this.output = output;
        this.game = game;
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
