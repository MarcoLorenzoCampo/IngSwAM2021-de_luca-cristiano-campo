package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Action to activate a LeaderCard. Verifies different steps:
 * 1) Validating the game state;
 * 2) Validating the sender matches the current player;
 * 3) Validating card can be placed and requirements are matched.
 */
public class PlaceLeaderAction extends Action {

    private final PossibleAction actionTag = PossibleAction.PLACE_LEADER_CARD;

    private final int leaderToActivate;



    /**
     * Public builder for this action.
     * @param actionSender: the Name of the player who requested this action;
     * @param leaderToActivate: Card index to activate;
     */
    public PlaceLeaderAction(String actionSender, int leaderToActivate) {
        super.setActionSender(actionSender);
        this.leaderToActivate = leaderToActivate;
    }

    public int getLeaderToActivate() {
        return leaderToActivate;
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
