package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.MultiplayerGame;
import it.polimi.ingsw.actions.Action;

import java.io.FileNotFoundException;

/**
 * Utility class  for basic action handling.
 */
public final class ActionManager {

    public static void actionReceiver(Action receivedAction) {

        try {
            MultiplayerGame.getGameInstance().getCurrentPlayer().getPlayerBoard().getAction(receivedAction);
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
