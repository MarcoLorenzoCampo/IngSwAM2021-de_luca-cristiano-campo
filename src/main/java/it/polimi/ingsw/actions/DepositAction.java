package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

public class DepositAction extends Action{
    private final PossibleAction actionTag = PossibleAction.DEPOSIT;
    private final int index;

    private final IGame game;

    public DepositAction(int index, String actionSender, IGame game) {
        this.index = index;
        super.setActionSender(actionSender);
        this.game = game;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
