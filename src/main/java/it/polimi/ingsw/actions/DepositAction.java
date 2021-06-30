package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Action to deposit a resource from the buffer to the warehouse, it holds the position
 * of the resource the player wants to add in the warehouse
 */
public class DepositAction extends Action{
    private final PossibleAction actionTag = PossibleAction.DEPOSIT;
    private final int index;


    public DepositAction(int index, String actionSender) {
        this.index = index;
        super.setActionSender(actionSender);
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
