package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.GetResourceFromMarketException;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.utilities.ActionValidator;

public class GetResourceFromMarketAction extends Action {

    private final PossibleAction actionTag = PossibleAction.GET_RESOURCE_FROM_MARKET;
    private final String actionSender;
    private final int indexToPickFrom;

    private final IGame game;

    /**
     * Public builder for this action.
     * @param actionSender the Name of the player who requested this action
     * @param indexToPickFrom the slot in which the card should be placed
     */
    public GetResourceFromMarketAction(String actionSender, int indexToPickFrom, IGame game) {
        this.actionSender = actionSender;
        this.indexToPickFrom = indexToPickFrom;
        this.game = game;
    }

    /**
     * Verifies via the {@link ActionValidator} if the action can be performed.
     * @throws InvalidPlayerException: wrong current player
     * @throws InvalidGameStateException: wrong game state to perform this action
     * @throws GetResourceFromMarketException: action refused because of the current player state.
     */
    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.validateGetFromMarket();

        runAction();
    }

    /**
     * Action to perform if verified.
     */
    private void runAction() {
        this.game.getIGameBoard()
                .getResourceMarket()
                .pickResources(indexToPickFrom);

        this.game.getCurrentPlayer()
                .getPlayerState()
                .performedExclusiveAction();
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
