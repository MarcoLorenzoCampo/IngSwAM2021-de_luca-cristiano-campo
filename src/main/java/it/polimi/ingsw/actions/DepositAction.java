package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;

public class DepositAction extends Action{
    private final int index;
    private final String actionSender;

    private final IGame game;


    public DepositAction(int index, String actionSender, IGame game) {
        this.index = index;
        this.actionSender = actionSender;
        this.game = game;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.validateOutOfBounds(index);

        runAction();


    }

    private void runAction(){
        try {
            this.game.getCurrentPlayer()
                    .getInventoryManager()
                    .addResourceToWarehouse(index);
        } catch (DiscardResourceException exception) {
            this.game.getCurrentPlayer()
                    .getInventoryManager()
                    .removeFromBuffer(index);
            //and tell everyone to move forward one faith point
        }
    }
}
