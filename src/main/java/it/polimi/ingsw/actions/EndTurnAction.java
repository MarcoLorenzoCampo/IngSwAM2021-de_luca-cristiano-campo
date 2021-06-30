package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Action to notify a player's turn has ended and the MultiPLayerLobbyManager should get ready to
 * accept messages from the next player.
 */
public class EndTurnAction extends Action {

    private static final PossibleAction actionTag = PossibleAction.END_TURN;


    public EndTurnAction(String actionSender) {
        super.setActionSender(actionSender);

    }

    public static PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
