package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

import static it.polimi.ingsw.enumerations.ResourceType.UNDEFINED;

public class ChangeMarbleAction extends Action{
    private final PossibleAction actionTag = PossibleAction.CHANGE_COLOR;

    private final ResourceType color;
    private final int index;

    private final IGame game;

    public ChangeMarbleAction(String sender, ResourceType type, int number, IGame current){
        super.setActionSender(sender);
        this.color = type;
        this.index = number;
        this.game = current;
    }

    public int getIndex() {
        return index;
    }

    public ResourceType getColor() {
        return color;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(super.getActionSender());
        ActionValidator.validateColorExchange(color);
        ActionValidator.validateIndex(index);

        runAction();
    }

    private void runAction() throws EndGameException {
        this.game.getCurrentPlayer()
                .getPlayerBoard()
                .getInventoryManager()
                .customExchange(index, color);

        if(!game.getCurrentPlayer()
                .getPlayerBoard()
                .getInventoryManager()
                .getBuffer()
                .stream()
                .anyMatch(MaterialResource -> MaterialResource.getResourceType().equals(UNDEFINED))){
            game.getCurrentPlayer()
                    .getPlayerState()
                    .setCanDeposit(true);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
