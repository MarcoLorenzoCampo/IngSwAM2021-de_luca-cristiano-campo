package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.actions.Action;

/**
 * Utility class  for basic action handling.
 */
public final class ActionManager {

    private final IGame currentGame;
    private final ILobbyManager caller;

    private boolean actionAccepted;

    public ActionManager(IGame currentGame, ILobbyManager caller) {
        this.currentGame = currentGame;
        this.caller = caller;
        this.actionAccepted = true;
    }

    /**
     * Calls the {@link it.polimi.ingsw.model.player.RealPlayerBoard} method getAction() to
     * perform the action received (if validate).
     * Has more branches to deal with checked exceptions sent by the {@link it.polimi.ingsw.model.utilities.ActionValidator};
     * @param receivedAction: action sent by the player via network.
     */
    public void actionReceiver(Action receivedAction) {

        try {
            currentGame.getCurrentPlayer().getPlayerBoard().getAction(receivedAction);
        } catch (InvalidPlayerException e) {
            actionAccepted = false;
        } catch (InvalidGameStateException e) {
            actionAccepted = false;
        } catch (GetResourceFromMarketException e) {
            actionAccepted = false;
        } catch (BuyCardFromMarketException e) {
            actionAccepted = false;
        } catch (EndTurnException e) {

            caller.setNextTurn();
        } catch (NoMatchingRequisitesException e) {
            actionAccepted = false;
        } catch (LeaderCardException e) {
            actionAccepted = false;
        } catch (EndGameException e) {
            e.printStackTrace();
        } catch (InvalidProductionSlotException e) {
            actionAccepted = false;
        } catch (MustPerformActionException e) {
            actionAccepted = false;
        }
    }

    public boolean isActionAccepted() {
        return actionAccepted;
    }
}
