package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.actions.Action;

/**
 * Utility class  for basic action handling.
 */
public final class ActionManager {

    /**
     * Calls the {@link it.polimi.ingsw.model.player.RealPlayerBoard} method getAction() to
     * perform the action received (if validate).
     * Has more branches to deal with checked exceptions sent by the {@link it.polimi.ingsw.model.utilities.ActionValidator};
     * @param receivedAction: action sent by the player via network.
     */
    public static void actionReceiver(Action receivedAction) {

        try {
            Game.getGameInstance().getCurrentPlayer().getPlayerBoard().getAction(receivedAction);
        } catch (InvalidPlayerException e) {
            //sends message to the sender saying he isn't allowed to play now
        } catch (InvalidGameStateException e) {
            //
        } catch (GetResourceFromMarketException e) {
            //
        } catch (BuyCardFromMarketException e) {
            //
        } catch (EndTurnException e) {
            //send notification to the players
            LobbyManager.setNextTurn();
        } catch (NoMatchingRequisitesException e) {
            //
        } catch (LeaderCardException e) {
            //
        } catch (EndGameException e) {
            e.printStackTrace();
        }
    }
}
