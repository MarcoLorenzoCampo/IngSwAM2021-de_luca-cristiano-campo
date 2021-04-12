package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.ActionValidator;

public class GetResourceFromMarketAction implements Action {

    private final PossibleAction actionTag = PossibleAction.GET_RESOURCE_FROM_MARKET;
    String actionSender;
    int index;

    public GetResourceFromMarketAction(String actionSender, int index) {
        this.actionSender = actionSender;
        this.index = index;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.validateGetFromMarket(index);

        runAction();
    }

    private void runAction() {
        Game.getGameInstance()
                .getGameBoard()
                .getResourceMarket()
                .pickResources(index);
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
