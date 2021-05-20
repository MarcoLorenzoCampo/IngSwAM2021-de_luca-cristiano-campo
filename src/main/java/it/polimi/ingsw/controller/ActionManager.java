package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.actions.Action;

/**
 * Utility class  for basic action handling.
 */
public final class ActionManager {

    private final IGame currentGame;
    private final GameManager gameManager;

    private boolean actionAccepted;

    public ActionManager(IGame currentGame, GameManager gameManager) {
        this.currentGame = currentGame;
        this.gameManager = gameManager;
        this.actionAccepted = true;
    }

    /**
     * Calls the {@link it.polimi.ingsw.model.player.RealPlayerBoard} method getAction() to
     * perform the action received (if validate).
     * Has more branches to deal with checked exceptions sent by the ActionValidator
     * {@link ActionValidator};
     * @param receivedAction: action sent by the player via network.
     */
    public void onReceiveAction(Action receivedAction) {

        try {
            currentGame.getCurrentPlayer()
                    .getPlayerBoard()
                    .getAction(receivedAction);

        } catch (InvalidPlayerException e) {
            actionAccepted = false;
        } catch (InvalidGameStateException e) {
            actionAccepted = false;
        } catch (GetResourceFromMarketException e) {
            actionAccepted = false;
        } catch (BuyCardFromMarketException e) {
            gameManager.getCurrentView().showGenericString(" ");
            actionAccepted = false;
        } catch (EndTurnException e) {

            //gameManager.getLobbyManager().setNextTurn();

        } catch (NoMatchingRequisitesException e) {
            gameManager.getCurrentView()
                    .showInvalidAction("You don't fulfill the requirements to buy this card!\n");

        } catch (LeaderCardException e) {
            actionAccepted = false;
        } catch (EndGameException e) {
            e.printStackTrace();
        } catch (InvalidProductionSlotException e) {
            actionAccepted = false;
        } catch (MustPerformActionException e) {
            actionAccepted = false;
        } catch (IndexOutOfBoundsException e) {
            actionAccepted = false;
        }
    }

    public boolean isActionAccepted() {
        return actionAccepted;
    }
}
