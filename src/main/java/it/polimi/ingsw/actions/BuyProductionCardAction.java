package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.controller.ActionValidator;

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
    private final String actionSender;
    private final ProductionCard boughtCard;
    private final int destinationSlot;

    private final IGame game;

    /**
     * Public constructor for this action.
     * @param actionSender: the Name of the player who requested this action;
     * @param boughtCard: card to be purchased;
     * @param destinationSlot: destination slot in the ProductionBoard;
     */
    public BuyProductionCardAction(String actionSender, ProductionCard boughtCard, int destinationSlot, IGame game) {
        this.actionSender = actionSender;
        this.boughtCard = boughtCard;
        this.destinationSlot = destinationSlot;
        this.game = game;
    }

    /**
     * Verifies via the {@link ActionValidator} if the action can be performed.
     * @throws InvalidPlayerException: wrong current player
     * @throws InvalidGameStateException: wrong game state to perform this action
     * @throws BuyCardFromMarketException: action refused because of the current player state.
     * @throws NoMatchingRequisitesException: the player doesn't own enough resources to buy the specified card;
     * @throws EndGameException: if this is the 7th card bought by the player;
     * @throws InvalidProductionSlotException: the production slot isn't in a valid state to place the card;
     */
    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, BuyCardFromMarketException,
            NoMatchingRequisitesException, EndGameException, InvalidProductionSlotException {

        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.validateBuyCardFromMarketAction(boughtCard);
        ActionValidator.validateProductionSlot(destinationSlot, boughtCard);

        runAction();
    }

    private void runAction() throws EndGameException {
        this.game.getGameBoard()
                .getProductionCardMarket()
                .buyCard(boughtCard);

        boughtCard.placeCard(destinationSlot, boughtCard);

        this.game.getCurrentPlayer()
                .getPlayerState()
                .performedExclusiveAction();
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
