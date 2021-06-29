package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

/**
 * Action that takes the index of the row or column the player wants to buy
 */
public class GetResourceFromMarketAction extends Action {

    private final PossibleAction actionTag = PossibleAction.GET_RESOURCE_FROM_MARKET;
    private final int indexToPickFrom;



    /**
     * Public builder for this action.
     * @param actionSender the Name of the player who requested this action
     * @param indexToPickFrom the slot in which the card should be placed
     */
    public GetResourceFromMarketAction(String actionSender, int indexToPickFrom) {
        super.setActionSender(actionSender);
        this.indexToPickFrom = indexToPickFrom;
    }

    public int getIndexToPickFrom() {
        return indexToPickFrom;
    }

    public PossibleAction getActionTag() {
        return actionTag;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
