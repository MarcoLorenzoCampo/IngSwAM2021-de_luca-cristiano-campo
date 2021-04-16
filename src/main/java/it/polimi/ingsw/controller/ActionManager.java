package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.actions.Action;

/**
 * Utility class  for basic action handling.
 */
public final class ActionManager {

    public static void actionReceiver(Action receivedAction) {

        try {
            Game.getGameInstance().getCurrentPlayer().getPlayerBoard().getAction(receivedAction);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        } catch (InvalidGameStateException e) {
            //
        } catch (GetResourceFromMarketException e) {
            //
        } catch (BuyCardFromMarketException e) {
            //
        } catch (EndTurnException e) {
            //
        } catch (NoMatchingRequisitesException e) {
            //
        } catch (LeaderCardException e) {
            //
        }
    }
}
