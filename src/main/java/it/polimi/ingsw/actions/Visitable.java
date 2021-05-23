package it.polimi.ingsw.actions;

import it.polimi.ingsw.model.player.Visitor;

public interface Visitable {
    public void accept(Visitor visitor);
}
