package it.polimi.ingsw.model.player;

import it.polimi.ingsw.actions.*;

public interface Visitor {
    public void visit(ActivateProductionAction action);
    public void visit(ActivateExtraProductionAction action);
    public void visit(ActivateBaseProductionAction action);
    public void visit(BuyProductionCardAction action);
    public void visit(ChangeMarbleAction action);
    public void visit(DepositAction action);
    public void visit(DiscardLeaderCardAction action);
    public void visit(EndTurnAction action);
    public void visit(ExecuteProductionAction action);
    public void visit(GetResourceFromMarketAction action);
    public void visit(PlaceLeaderAction action);
    public void visit(RearrangeInventoryAction action);
    public void visit(RemoveResourcesAction action);
    public void visit(LorenzoAction action);
}
