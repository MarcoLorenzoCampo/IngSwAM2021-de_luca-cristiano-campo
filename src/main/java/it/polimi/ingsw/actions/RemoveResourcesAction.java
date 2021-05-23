package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;
import it.polimi.ingsw.model.utilities.ResourceTag;

public class RemoveResourcesAction extends Action {
    private final PossibleAction actionTag = PossibleAction.REMOVE_RESOURCE;
    private final String source;
    private final ResourceTag toBeRemoved;


    private final IGame game;

    public RemoveResourcesAction(String actionSender, String source_message, ResourceTag toBeRemoved, IGame game) {
        super.setActionSender(actionSender);
        this.source = source_message;
        this.toBeRemoved = toBeRemoved;
        this.game = game;
    }

    public String getSource() {
        return source;
    }

    public ResourceTag getToBeRemoved() {
        return toBeRemoved;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(getActionSender());

        runAction();
    }

    private void runAction() {
        if(source.equals("WAREHOUSE")){
            try {
                game.getCurrentPlayer()
                        .getPlayerBoard()
                        .getInventoryManager()
                        .removeFromWarehouse(toBeRemoved);
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
                        .removeFromStrongbox(toBeRemoved);
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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
