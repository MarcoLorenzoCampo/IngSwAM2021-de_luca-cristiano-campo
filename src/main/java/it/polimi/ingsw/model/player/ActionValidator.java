package it.polimi.ingsw.model.player;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.InvalidActionException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.model.Game;


/**
 * Utility class to handle actions.
 */
public final class ActionValidator {

    private ActionValidator() {
        throw new UnsupportedOperationException("Utility class.");
    }

    public static void gameStateValidation() throws InvalidActionException {
        if (Game.getGameInstance().getCurrentState().getGameState().equals(PossibleGameStates.SETUP))
                throw new InvalidActionException();
    }

    /**
     * checks if the sender is the current player
     * @param actionSender name of the sender.
     * @throws InvalidPlayerException if it's not the current player,
     * throw exception.
     */
    public static void senderValidation(String actionSender) throws InvalidPlayerException {

        if(!actionSender.equals(Game.getGameInstance().getCurrentPlayer().getName()))
            throw new InvalidPlayerException();
    }

    /**
     * @param index reference to pick resources from market
     * @throws InvalidActionException action isn't allowed
     */
    public static void validateGetFromMarket(int index) throws InvalidActionException {
        if(Game.getGameInstance().getCurrentPlayer().getPlayerState().getHasPickedResources())
            throw new InvalidActionException();
    }


    public static void validateBuyProductionCardAction() {

    }
}
