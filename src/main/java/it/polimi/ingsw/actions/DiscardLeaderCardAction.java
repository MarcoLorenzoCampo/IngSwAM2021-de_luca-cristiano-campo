package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.exceptions.LeaderCardException;
import it.polimi.ingsw.exceptions.NoMatchingRequisitesException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.utilities.ActionValidator;

public class DiscardLeaderCardAction extends Action {

    private final PossibleAction actionTag = PossibleAction.DISCARD_LEADER_CARD;

    String actionSender;
    int leaderToDiscard;

    public DiscardLeaderCardAction(String actionSender, int leaderToDiscard) {
        this.actionSender = actionSender;
        this.leaderToDiscard = leaderToDiscard;
    }

    @Override
    public void isValid() throws InvalidGameStateException, InvalidPlayerException,
            LeaderCardException, NoMatchingRequisitesException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.leaderValidator(leaderToDiscard);

        runAction();
    }

    private void runAction() {
        Game.getGameInstance()
                .getCurrentPlayer()
                .getPlayerBoard()
                .getOwnedLeaderCards()
                .remove(leaderToDiscard);
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
