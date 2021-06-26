package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;
import it.polimi.ingsw.model.utilities.ResourceTag;

public class RemoveResourcesAction extends Action {
    private final PossibleAction actionTag = PossibleAction.REMOVE_RESOURCE;
    private final String source;
    private final ResourceTag toBeRemoved;

    private final IGame game;

    public RemoveResourcesAction(String actionSender, String source_message, ResourceTag toBeRemoved, IGame game) {
        super.setActionSender(actionSender);
        this.source = source_message;
        this.toBeRemoved = toBeRemoved;
        this.game = game;
    }

    public String getSource() {
        return source;
    }

    public ResourceTag getToBeRemoved() {
        return toBeRemoved;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
