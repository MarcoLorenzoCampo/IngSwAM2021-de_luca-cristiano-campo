package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

public class DepositAction extends Action{
    private final PossibleAction actionTag = PossibleAction.DEPOSIT;
    private final int index;

    private final IGame game;


    public DepositAction(int index, String actionSender, IGame game) {
        this.index = index;
        super.setActionSender(actionSender);
        this.game = game;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(super.getActionSender());
        ActionValidator.validateOutOfBounds(index);

        runAction();
    }

    private void runAction(){
        try {
            this.game.getCurrentPlayer()
                    .getPlayerBoard()
                    .getInventoryManager()
                    .addResourceToWarehouse(index);
        } catch (DiscardResourceException exception) {
            this.game.getCurrentPlayer()
                    .getPlayerBoard()
                    .getInventoryManager()
                    .removeFromBuffer(index);
            //and tell everyone to move forward one faith point
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
