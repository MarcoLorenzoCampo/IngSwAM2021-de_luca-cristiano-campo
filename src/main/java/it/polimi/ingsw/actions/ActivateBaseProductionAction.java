package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.player.Visitor;

public class ActivateBaseProductionAction extends Action {
    private final PossibleAction actiontag = PossibleAction.ACTIVATE_PRODUCTION;


    private final ResourceType input_1;
    private final ResourceType input_2;
    private final ResourceType output;

    private final IGame game;

    public ActivateBaseProductionAction (String actionSender,ResourceType one, ResourceType two , ResourceType out, IGame game){

        super.setActionSender(actionSender);
        this.input_1 = one;
        this.input_2 = two;
        this.output = out;
        this.game = game;
    }

    public ResourceType getInput_1() {
        return input_1;
    }

    public ResourceType getInput_2() {
        return input_2;
    }

    public ResourceType getOutput() {
        return output;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

