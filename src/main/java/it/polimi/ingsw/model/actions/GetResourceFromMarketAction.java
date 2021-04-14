package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.GetResourceFromMarketException;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.utilities.ActionValidator;

public class GetResourceFromMarketAction extends Action {

    private final PossibleAction actionTag = PossibleAction.GET_RESOURCE_FROM_MARKET;
    private final String actionSender;
    private final int index;

    public GetResourceFromMarketAction(String actionSender, int index) {
        this.actionSender = actionSender;
        this.index = index;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.validateGetFromMarket();

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
