package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Action to notify a player's turn has ended and the MultiPLayerLobbyManager should get ready to
 * accept messages from the next player.
 * Basic validation:
 * 1) Verifies the sender is the current player;
 * 2) Verifies the game state is adequate.
 */
public class EndTurnAction extends Action {

    private static final PossibleAction actionTag = PossibleAction.END_TURN;

    private final IGame game;

    public EndTurnAction(String actionSender, IGame game) {
        super.setActionSender(actionSender);
        this.game = game;

    }

    public static PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
