package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.utilities.ActionValidator;

public class BuyProductionCardAction extends Action {

    private final PossibleAction actionTag = PossibleAction.BUY_PRODUCTION_CARD;
    private final String actionSender;
    private final ProductionCard boughtCard;

    public BuyProductionCardAction(String actionSender, ProductionCard boughtCard) {
        this.actionSender = actionSender;
        this.boughtCard = boughtCard;
    }

    @Override
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, BuyCardFromMarketException, NoMatchingRequisitesException, EndGameException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.validateBuyCardFromMarketAction(boughtCard);

        runAction();
    }

    private void runAction() throws EndGameException {
        Game.getGameInstance()
                .getGameBoard()
                .getProductionCardMarket()
                .buyCard(boughtCard);

        boughtCard.placeCard();

        ActionValidator.performedExclusiveAction();
    }

    public String getActionSender() {
        return actionSender;
    }

    public ProductionCard getBoughtCard() {
        return boughtCard;
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
