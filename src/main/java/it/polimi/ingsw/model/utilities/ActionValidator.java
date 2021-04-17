package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.MultiplayerGame;
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
        if (MultiplayerGame.getGameInstance().getCurrentState().getGameState().equals(PossibleGameStates.SETUP))
                throw new InvalidGameStateException();
    }

    /**
     * Verifies if the player performing the action is the current player.
     * @param actionSender name of the sender.
     * @throws InvalidPlayerException if it's not the current player,
     * throw exception.
     */
    public static void senderValidation(String actionSender) throws InvalidPlayerException {
        if(!actionSender.equals(MultiplayerGame.getGameInstance().getCurrentPlayer().getName()))
            throw new InvalidPlayerException();
    }

    /**
     * Checks if player has already performed on of the exclusive actions.
     * @throws GetResourceFromMarketException action isn't allowed
     */
    public static void validateGetFromMarket() throws GetResourceFromMarketException {
        if(MultiplayerGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasPickedResources())
            throw new GetResourceFromMarketException();
    }

    /**
     * Validating the buy form market action. Multiple validations have to be checked in order for
     * the action to be executed.
     * @param boughtCard is what the player wants to buy
     * @throws BuyCardFromMarketException the action has already been performed
     * @throws NoMatchingRequisitesException the requisites to buy the card aren't satisfied
     */
    public static void validateBuyCardFromMarketAction(ProductionCard boughtCard) throws BuyCardFromMarketException, NoMatchingRequisitesException {
        if(MultiplayerGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasBoughCard())
            throw new BuyCardFromMarketException();

        if(!validateProductionCardRequirements(boughtCard))
            throw new NoMatchingRequisitesException();
    }

    /**
     *
     * @param toValidate Production Card to validate.
     * @return returns the outcome of the validation. true means the card can be purchased,
     * false means the caller will throw an exception.
     */
    private static boolean validateProductionCardRequirements(ProductionCard toValidate) {
        List<ResourceTag> requirements = toValidate.getRequirements();
        Map<ResourceType, Integer> actualInventory = MultiplayerGame.getGameInstance().getCurrentPlayer().getInventoryManager().getInventory();

        for (ResourceTag requirement : requirements) {
            if(requirement.getQuantity() > actualInventory.get(requirement.getType()))
                return false;
        }
        return true;
    }

    /**
     * Verifies a leader card can be discarded.
     * @throws LeaderCardException generic exception regarding leader cards.
     */
    public static void leaderValidator(int index) throws LeaderCardException {
        if(!MultiplayerGame.getGameInstance().getCurrentPlayer().getPlayerState().getHasPlaceableLeaders()
            || index >= MultiplayerGame.getGameInstance().getCurrentPlayer().getPlayerBoard().getOwnedLeaderCards().size())
            throw new LeaderCardException("You don't own that card!");
    }

    /**
     * whenever one exclusive action is run, the player state will be changed, making future validations
     * for the exclusive actions always throw exceptions.
     */
    public static void performedExclusiveAction() {
        MultiplayerGame.getGameInstance()
                .getCurrentPlayer()
                .getPlayerState()
                .performedExclusiveAction();
    }

}
