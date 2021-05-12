package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.EndTurnException;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.exceptions.MustPerformActionException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.controller.ActionValidator;

/**
 * Action to notify a player's turn has ended and the MultiPLayerLobbyManager should get ready to
 * accept messages from the next player.
 * Basic validation:
 * 1) Verifies the sender is the current player;
 * 2) Verifies the game state is adequate.
 */
public class EndTurnAction extends Action {

    private static final PossibleAction actionTag = PossibleAction.END_TURN;
    private final String actionSender;

    private final IGame game;

    public EndTurnAction(String actionSender, IGame game) {
        this.actionSender = actionSender;
        this.game = game;

    }

    /**
     * @throws InvalidPlayerException: the Name of the player who requested this action;
     * @throws InvalidGameStateException: GameState isn't adequate to receive the input.
     * @throws EndTurnException: notifies the controller current turn is over.
     */
    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, EndTurnException, MustPerformActionException {

        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.canEndTurnValidator();

        this.game.getCurrentPlayer()
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
