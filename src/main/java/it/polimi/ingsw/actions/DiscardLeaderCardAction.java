package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Action used to discard a leader and gain one faith point,
 * it holds the index of the leader the player wants to discard
 */
public class DiscardLeaderCardAction extends Action {

    private final PossibleAction actionTag = PossibleAction.DISCARD_LEADER_CARD;


    private final int leaderToDiscard;


    public DiscardLeaderCardAction(String actionSender, int leaderToDiscard) {
        super.setActionSender(actionSender);
        this.leaderToDiscard = leaderToDiscard;
    }

    public int getLeaderToDiscard() {
        return leaderToDiscard;
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
