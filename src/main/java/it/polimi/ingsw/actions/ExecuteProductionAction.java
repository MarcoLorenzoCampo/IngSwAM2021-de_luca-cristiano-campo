package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;
import it.polimi.ingsw.model.utilities.MaterialResource;

public class ExecuteProductionAction extends Action {
    private final PossibleAction actionTag = PossibleAction.EXECUTE_PRODUCTION;
    private final IGame game;

    public ExecuteProductionAction(String actionSender, IGame game) {
        super.setActionSender(actionSender);
        this.game = game;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(getActionSender());
        ActionValidator.validateFinalProduction();

        runAction();
    }

    private void runAction() {
        game.getCurrentPlayer()
                .getPlayerBoard()
                .getProductionBoard()
                .executeProduction(game.getCurrentPlayer().getPlayerBoard());

        game.getCurrentPlayer().getPlayerBoard().getInventoryManager().addResourceToStrongbox();

        game.getCurrentPlayer().getPlayerState().performedExclusiveAction();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
