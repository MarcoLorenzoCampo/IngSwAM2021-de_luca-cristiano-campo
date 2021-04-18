package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.GetResourceFromMarketException;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.utilities.ActionValidator;

public class GetResourceFromMarketAction extends Action {

    private final PossibleAction actionTag = PossibleAction.GET_RESOURCE_FROM_MARKET;
    private final String actionSender;
    private final int indexToPickFrom;

    /**
     * Public builder for this action.
     * @param actionSender the Name of the player who requested this action
     * @param indexToPickFrom the slot in which the card should be placed
     */
    public GetResourceFromMarketAction(String actionSender, int indexToPickFrom) {
        this.actionSender = actionSender;
        this.indexToPickFrom = indexToPickFrom;
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
        Game.getGameInstance()
                .getGameBoard()
                .getResourceMarket()
                .pickResources(indexToPickFrom);
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
