package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.EndTurnException;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.model.MultiplayerGame;
import it.polimi.ingsw.model.utilities.ActionValidator;

public class EndTurnAction extends Action {

    private static final PossibleAction actionTag = PossibleAction.END_TURN;
    private final String actionSender;

    public EndTurnAction(String actionSender) {
        this.actionSender = actionSender;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, EndTurnException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);

        MultiplayerGame.getGameInstance()
                .getCurrentPlayer()
                .getPlayerState()
                .endTurnReset();

        throw new EndTurnException(actionSender);
    }

    public static PossibleAction getActionTag() {
        return actionTag;
    }

    public String getActionSender() {
        return actionSender;
    }
}
