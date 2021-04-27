package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.ProductionCard;

import java.util.List;
import java.util.Map;


/**
 * Utility class to handle actions.
 */
public final class ActionValidator {

    private ActionValidator() {
        throw new UnsupportedOperationException("Utility class.");
    }

    /**
     * Highest level validation: if the game isn't ready to receive inputs, it will throw an exception.
     * @throws InvalidGameStateException if what stated above happens.
     */
    public static void gameStateValidation() throws InvalidGameStateException {
        if (PlayingGame.getGameInstance().getCurrentState().getGameState().equals(PossibleGameStates.SETUP))
                throw new InvalidGameStateException();
    }

    /**
     * Verifies if the player performing the action is the current player.
     * @param actionSender name of the sender.
     * @throws InvalidPlayerException if it's not the current player,
     * throw exception.
     */
    public static void senderValidation(String actionSender) throws InvalidPlayerException {
        if(!actionSender.equals(PlayingGame.getGameInstance().getCurrentPlayer().getName()))
            throw new InvalidPlayerException();
    }

    public static void canEndTurnValidator() throws MustPerformActionException {
        if(!PlayingGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasPickedResources()
        || !PlayingGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasBoughCard()
        || !PlayingGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasActivatedProductions()) {
            throw new MustPerformActionException();
        }
    }

    /**
     * Checks if player has already performed on of the exclusive actions.
     * @throws GetResourceFromMarketException action isn't allowed
     */
    public static void validateGetFromMarket() throws GetResourceFromMarketException {
        if(PlayingGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasPickedResources())
            throw new GetResourceFromMarketException();
    }

    /**
     * Validating the buy form market action. Multiple validations have to be checked in order for
     * the action to be executed.
     * @param boughtCard is what the player wants to buy
     * @throws BuyCardFromMarketException the action has already been performed
     * @throws NoMatchingRequisitesException the requisites to buy the card aren't satisfied
     */
    public static void validateBuyCardFromMarketAction(ProductionCard boughtCard)
            throws BuyCardFromMarketException, NoMatchingRequisitesException {

        if(PlayingGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasBoughCard())
            throw new BuyCardFromMarketException();

        if(!validateProductionCardRequirements(boughtCard))
            throw new NoMatchingRequisitesException();
    }

    /**
     * @param toValidate Production Card to validate.
     * @return returns the outcome of the validation. true means the card can be purchased,
     * false means the caller will throw an exception.
     */
    private static boolean validateProductionCardRequirements(ProductionCard toValidate) {
        List<ResourceTag> requirements = toValidate.getRequirements();
        Map<ResourceType, Integer> actualInventory = PlayingGame.getGameInstance().getCurrentPlayer()
                .getInventoryManager().getInventory();

        for (ResourceTag requirement : requirements) {
            if(requirement.getQuantity() > actualInventory.get(requirement.getType()))
                return false;
        }
        return true;
    }

    /**
     * Validates if the {@link it.polimi.ingsw.actions.BuyProductionCardAction} provided a valid slot
     * to place the card once other steps have been validated.
     * @param productionSlotIndex: index in which the card should be placed.
     * @throws InvalidProductionSlotException: notifies the controller the destination slot is invalid.
     */
    public static void validateProductionSlot(int productionSlotIndex, ProductionCard boughtCard)
            throws InvalidProductionSlotException {

        if(!PlayingGame.getGameInstance().getCurrentPlayer().getPlayerBoard().getProductionBoard()
                .checkPutCard(productionSlotIndex, boughtCard)) {
            throw new InvalidProductionSlotException();
        }
    }

    /**
     * Validates the ownership of a specific leader card
     * @param index: card to verify.
     * @throws LeaderCardException: generic exception thrown by leader actions.
     */
    public static void discardLeaderValidator(int index) throws LeaderCardException {
        if(!PlayingGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasPlaceableLeaders()
                || index >= PlayingGame.getGameInstance().getCurrentPlayer().getOwnedLeaderCards().size()) {
            throw new LeaderCardException("You don't own that card!");
        }
    }

    /**
     * Verifies a leader card can be placed.
     * @throws LeaderCardException generic exception regarding leader cards.
     */
    public static void leaderValidator(int index) throws LeaderCardException, NoMatchingRequisitesException {
        discardLeaderValidator(index);

        if(PlayingGame.getGameInstance().getCurrentPlayer().getOwnedLeaderCards().get(index).getRequirementsResource() == null) {
            if (validateLeaderDevTags(PlayingGame.getGameInstance()
                    .getCurrentPlayer().getOwnedLeaderCards().get(index).getRequirementsDevCards()))
                throw new NoMatchingRequisitesException();
        }

        if(PlayingGame.getGameInstance().getCurrentPlayer().getOwnedLeaderCards().get(index).getRequirementsDevCards() == null) {
            if (!validateLeaderResourceTags(PlayingGame.getGameInstance()
                    .getCurrentPlayer().getOwnedLeaderCards().get(index).getRequirementsResource()))
                throw new NoMatchingRequisitesException();
        }
    }

    /**
     * Validation of DevelopmentTags.
     */
    private static boolean validateLeaderDevTags(DevelopmentTag[] requirements) {
        for(DevelopmentTag iterator : requirements) {
            if(PlayingGame.getGameInstance().getCurrentPlayer().getPlayerBoard().getProductionBoard().getInventory()
                    .get(iterator.getColor())[iterator.getLevel().ordinal()]<iterator.getQuantity())
                return true;
        }
        return false;
    }

    /**
     * Validation of ResourceTags.
     */
    private static boolean validateLeaderResourceTags(ResourceTag[] requirements) {
        Map<ResourceType, Integer> actualInventory = PlayingGame.getGameInstance().getCurrentPlayer()
                .getInventoryManager().getInventory();

        for (ResourceTag requirement : requirements) {
            if(requirement.getQuantity() > actualInventory.get(requirement.getType()))
                return false;
        }
        return true;
    }
}
