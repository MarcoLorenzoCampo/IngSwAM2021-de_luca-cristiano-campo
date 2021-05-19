package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;

public class ActivateExtraProductionAction extends Action {
    private final PossibleAction actionTag = PossibleAction.ACTIVATE_PRODUCTION;
    private final String actionSender;
    private final int slot;
    private final ResourceType output;


    private final IGame game;


    public ActivateExtraProductionAction(String actionSender, int slot, ResourceType output, IGame game) {
        this.actionSender = actionSender;
        this.slot = slot;
        this.output = output;
        this.game = game;
    }

    public ResourceType getOutput() {
        return output;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.validateExtraProductionSlot(slot);
        ActionValidator.validateResourceProduction(output);

        runAction();
    }

    private void runAction() {
        game.getCurrentPlayer()
                .getPlayerBoard()
                .getProductionBoard()
                .selectLeaderProduction(slot, output);
    }
}