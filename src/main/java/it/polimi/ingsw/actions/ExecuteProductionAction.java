package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

public class ExecuteProductionAction extends Action {
    private final PossibleAction actionTag = PossibleAction.EXECUTE_PRODUCTION;


    public ExecuteProductionAction(String actionSender) {
        super.setActionSender(actionSender);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
