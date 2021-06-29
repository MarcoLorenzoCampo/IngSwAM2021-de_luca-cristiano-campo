package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.player.Visitor;

/**
 * This action contains two "micro-actions": buy a card and place it.
 * For this reason it requires multiple validation steps:
 *
 * Action to buy a ProductionCard and place it in a specific ProductionBoard slot.
 * Validation if made of different steps:
 * 1) Validating the game state;
 * 2) Validating the sender matches the current player;
 * 3) Validating card can be bought and requirements can be paid.
 * 4) Validating the production slot chosen is in a valid state to receive that card.
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
