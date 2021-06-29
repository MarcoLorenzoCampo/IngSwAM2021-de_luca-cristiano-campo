package it.polimi.ingsw.model.player;

import it.polimi.ingsw.actions.*;

public interface Visitor {
    void visit(ActivateProductionAction action);
    void visit(ActivateExtraProductionAction action);
    void visit(ActivateBaseProductionAction action);
    void visit(BuyProductionCardAction action);
    void visit(ChangeMarbleAction action);
    void visit(DepositAction action);
    void visit(DiscardLeaderCardAction action);
    void visit(EndTurnAction action);
    void visit(ExecuteProductionAction action);
    void visit(GetResourceFromMarketAction action);
    void visit(PlaceLeaderAction action);
    void visit(RemoveResourcesAction action);
    void visit(ClearProductionAction action);
}
