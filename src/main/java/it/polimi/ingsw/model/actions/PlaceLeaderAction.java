package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.exceptions.LeaderCardException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.ActionValidator;

public class PlaceLeaderAction extends Action {

    private final PossibleAction actionTag = PossibleAction.PLACE_LEADER_CARD;

    private final String actionSender;
    private final int leaderToActivate;

    public PlaceLeaderAction(String actionSender, int leaderToActivate) {
        this.actionSender = actionSender;
        this.leaderToActivate = leaderToActivate;
    }
    @Override
    public void isValid() throws InvalidGameStateException, InvalidPlayerException, LeaderCardException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.leaderValidator(leaderToActivate);
        //ActionValidator.checkLeaderRequirements();

        runAction();
    }

    private void runAction() {
        Game.getGameInstance()
                .getCurrentPlayer()
                .getPlayerBoard()
                .getOwnedLeaderCards()
                .get(leaderToActivate)
                .setActive();
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
