package it.polimi.ingsw.actions;

import it.polimi.ingsw.model.player.Visitor;

/**
 * Interface used by the actions, it is needed to implement the visitor pattern
 */
public interface Visitable {
    void accept(Visitor visitor);
}
