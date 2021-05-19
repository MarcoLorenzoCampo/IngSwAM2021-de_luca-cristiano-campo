package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.utilities.ResourceTag;

public class RemoveResourcesAction extends Action {
    private final PossibleAction actionTag = PossibleAction.REMOVE_RESOURCE;
    private final String actionSender;
    private final String source;


    private final IGame game;

    public RemoveResourcesAction(String actionSender, String source_message, IGame game) {
        this.actionSender = actionSender;
        this.source = source_message;
        this.game = game;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);

        runAction();
    }

    private void runAction() {
        if(source.equals("WAREHOUSE")){
            try {
                game.getCurrentPlayer()
                        .getPlayerBoard()
                        .getInventoryManager()
                        .removeFromWarehouse(game.getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().get(0));
            } catch (CannotRemoveResourceException e) {
                try {
                    game.getCurrentPlayer()
                            .getPlayerBoard()
                            .getInventoryManager()
                            .removeFromStrongbox(new ResourceTag(e.getType(), e.getQuantity()));
                } catch (CannotRemoveResourceException cannotRemoveResourceException) {
                    cannotRemoveResourceException.printStackTrace();
                }
            }
        }
        else{
            try {
                game.getCurrentPlayer()
                        .getPlayerBoard()
                        .getInventoryManager()
                        .removeFromStrongbox(game.getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().get(0));
            } catch (CannotRemoveResourceException e) {
                try {
                    game.getCurrentPlayer()
                            .getPlayerBoard()
                            .getInventoryManager()
                            .removeFromWarehouse(new ResourceTag(e.getType(), e.getQuantity()));
                } catch (CannotRemoveResourceException cannotRemoveResourceException) {
                    cannotRemoveResourceException.printStackTrace();
                }
            }
        }
    }
}
