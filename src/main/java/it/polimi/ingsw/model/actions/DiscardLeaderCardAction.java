package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;

public class DiscardLeaderCardAction extends Action{

    private final PossibleAction actionTag = PossibleAction.DISCARD_LEADER_CARD;

    @Override
    public void isValid() {

    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
