package it.polimi.ingsw.actions;

import it.polimi.ingsw.model.player.Visitor;

public class ClearProductionAction extends Action{
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
