package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Action to buy a ProductionCard and place it in a specific ProductionBoard slot.
 */
public class BuyProductionCardAction extends Action {

    private final PossibleAction actionTag = PossibleAction.BUY_PRODUCTION_CARD;
    private final ProductionCard boughtCard;
    private final int destinationSlot;

    /**
     * Public constructor for this action.
     * @param actionSender: the Name of the player who requested this action;
     * @param boughtCard: card to be purchased;
     * @param destinationSlot: destination slot in the ProductionBoard;
     */
    public BuyProductionCardAction(String actionSender, ProductionCard boughtCard, int destinationSlot) {
        super.setActionSender(actionSender);
        this.boughtCard = boughtCard;
        this.destinationSlot = destinationSlot;
    }

    public ProductionCard getBoughtCard() {
        return boughtCard;
    }

    public int getDestinationSlot() {
        return destinationSlot;
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
