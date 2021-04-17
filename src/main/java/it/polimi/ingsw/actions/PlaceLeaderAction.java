package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.exceptions.LeaderCardException;
import it.polimi.ingsw.model.MultiplayerGame;
import it.polimi.ingsw.model.utilities.ActionValidator;

import java.io.FileNotFoundException;

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
        MultiplayerGame.getGameInstance()
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
