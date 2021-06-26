package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

public class ActivateProductionAction extends Action {

    private final PossibleAction actionTag = PossibleAction.ACTIVATE_PRODUCTION;
    private final int slot;

    public ActivateProductionAction(String sender, int number, IGame current){
        super.setActionSender(sender);
        this.slot = number;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
