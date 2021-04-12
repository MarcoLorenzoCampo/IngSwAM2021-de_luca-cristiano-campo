package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;

public class PlaceLeaderAction implements Action {

    public PossibleAction getActionTag() {
        return actionTag;
    }

    private final PossibleAction actionTag = PossibleAction.PLACE_LEADER_CARD;

    @Override
    public void isValid() {
    }
}
