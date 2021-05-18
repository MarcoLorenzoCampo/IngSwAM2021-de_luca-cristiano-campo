package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionValidator;
import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.game.IGame;

import java.util.ArrayList;

public class ActivateBaseProductionAction extends Action {
    private final PossibleAction actiontag = PossibleAction.ACTIVATE_PRODUCTION;

    private final String actionSender;
    private final ResourceType input_1;
    private final ResourceType input_2;
    private final ResourceType output;

    private final IGame game;

    public ActivateBaseProductionAction (String actionSender,ResourceType one, ResourceType two , ResourceType out, IGame game){
        this.actionSender=actionSender;
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
    public void isValid() throws InvalidPlayerException, InvalidGameStateException, GetResourceFromMarketException, BuyCardFromMarketException, NoMatchingRequisitesException, EndTurnException, LeaderCardException, EndGameException, InvalidProductionSlotException, MustPerformActionException {
        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.validateBaseProduction(this);
        ActionValidator.validateResourceProduction(input_1);
        ActionValidator.validateResourceProduction(input_2);
        ActionValidator.validateResourceProduction(output);
        runAction();
    }

    private void runAction() {
        game.getCurrentPlayer()
                .getPlayerBoard()
                .getProductionBoard()
                .selectBaseProduction(input_1, input_2, output);
    }
}

