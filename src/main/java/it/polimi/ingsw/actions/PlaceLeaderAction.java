package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.controller.ActionValidator;
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

    private final IGame game;

    /**
     * Public builder for this action.
     * @param actionSender: the Name of the player who requested this action;
     * @param leaderToActivate: Card index to activate;
     */
    public PlaceLeaderAction(String actionSender, int leaderToActivate, IGame game) {
        super.setActionSender(actionSender);
        this.leaderToActivate = leaderToActivate;
        this.game = game;
    }

    public int getLeaderToActivate() {
        return leaderToActivate;
    }

    /**
     * Verifies via the {@link ActionValidator} if the action can be performed.
     * @throws InvalidPlayerException: wrong current player
     * @throws InvalidGameStateException: wrong game state to perform this action
     * @throws LeaderCardException : action refused because of the current player state.
     * @throws NoMatchingRequisitesException: leaderCard can't be placed since requirements aren't matched.
     */
    @Override
    public void isValid() throws InvalidGameStateException, InvalidPlayerException,
            LeaderCardException, NoMatchingRequisitesException {

        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(super.getActionSender());
        ActionValidator.leaderValidator(leaderToActivate);

        runAction();
    }

    private void runAction() {
        this.game.getCurrentPlayer()
                .getOwnedLeaderCards()
                .get(leaderToActivate)
                .setActive(game.getCurrentPlayer().getPlayerBoard());

        this.game.getCurrentPlayer()
                .getPlayerState()
                .placedLeader();
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
