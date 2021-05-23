package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

public class ActivateProductionAction extends Action {

    private final PossibleAction actionTag = PossibleAction.ACTIVATE_PRODUCTION;
    private final int slot;

    private final IGame game;

    public ActivateProductionAction(String sender, int number, IGame current){
        super.setActionSender(sender);
        this.slot = number;
        this.game = current;
    }

    public int getSlot() {
        return slot;
    }


    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(super.getActionSender());
        ActionValidator.validateProductionSlot(slot);

        runAction();
    }

    private void runAction() {
        game.getCurrentPlayer()
                .getPlayerBoard()
                .getProductionBoard()
                .selectProductionSlot(slot);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
