package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.actions.GetResourceFromMarketAction;
import it.polimi.ingsw.model.market.ProductionCard;


/**
 * Utility class to handle actions.
 */
public final class ActionValidator {

    private ActionValidator() {
        throw new UnsupportedOperationException("Utility class.");
    }


    public static void gameStateValidation() throws InvalidGameStateException {
        if (Game.getGameInstance().getCurrentState().getGameState().equals(PossibleGameStates.SETUP))
                throw new InvalidGameStateException();
    }

    /**
     * Verifies if the player performing the action is the current player.
     * @param actionSender name of the sender.
     * @throws InvalidPlayerException if it's not the current player,
     * throw exception.
     */
    public static void senderValidation(String actionSender) throws InvalidPlayerException {
        if(!actionSender.equals(Game.getGameInstance().getCurrentPlayer().getName()))
            throw new InvalidPlayerException();
    }

    /**
     * Checks if player has already performed on of the exclusive actions.
     * @throws GetResourceFromMarketException action isn't allowed
     */
    public static void validateGetFromMarket() throws GetResourceFromMarketException {
        if(Game.getGameInstance().getCurrentPlayer().getPlayerState().getHasPickedResources())
            throw new GetResourceFromMarketException();
    }

    public static void validateBuyCardFromMarketAction(ProductionCard boughtCard) throws BuyCardFromMarketException {
        if(Game.getGameInstance().getCurrentPlayer().getPlayerState().getHasBoughCard())
            throw new BuyCardFromMarketException();
        //inventory manager check requisites throw new InvalidActionException();
    }

    public static void validateCardRequisites(ProductionCard toValidate) {

    }

    public static void performedExclusiveAction() {
        Game.getGameInstance()
                .getCurrentPlayer()
                .getPlayerState()
                .performedExclusiveAction();
    }

}
