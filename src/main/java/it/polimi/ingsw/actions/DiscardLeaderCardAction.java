package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleAction;
import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.InvalidGameStateException;
import it.polimi.ingsw.exceptions.InvalidPlayerException;
import it.polimi.ingsw.exceptions.LeaderCardException;
import it.polimi.ingsw.exceptions.NoMatchingRequisitesException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.controller.ActionValidator;

public class DiscardLeaderCardAction extends Action {

    private final PossibleAction actionTag = PossibleAction.DISCARD_LEADER_CARD;

    private final String actionSender;
    private final int leaderToDiscard;

    private final IGame game;

    public DiscardLeaderCardAction(String actionSender, int leaderToDiscard, IGame game) {
        this.actionSender = actionSender;
        this.leaderToDiscard = leaderToDiscard;
        this.game = game;
    }

    @Override
    public void isValid() throws InvalidGameStateException, InvalidPlayerException,
            LeaderCardException, NoMatchingRequisitesException {

        ActionValidator.gameStateValidation();
        ActionValidator.senderValidation(actionSender);
        ActionValidator.discardLeaderValidator(leaderToDiscard);

        /*if(!game.getCurrentState().getGameState().equals(PossibleGameStates.SETUP_LEADER)) {
            runAction();
        }*/

        runAction();
    }

    private void runAction() {
        if(game.getCurrentState().getGameState().equals(PossibleGameStates.SETUP_LEADER)) {
            this.game.getCurrentPlayer()
                    .setupLeaderCard(leaderToDiscard);
        } else {
            this.game.getCurrentPlayer()
                    .discardLeaderCard(leaderToDiscard);
        }

        if(!game.getCurrentState().getGameState().equals(PossibleGameStates.SETUP_LEADER))
            this.game.getCurrentPlayer();

        if(!game.getCurrentState().getGameState().equals(PossibleGameStates.SETUP_LEADER)) {
            this.game.getCurrentPlayer()
                    .getPlayerBoard()
                    .getFaithTrack()
                    .increaseFaithMarker();
        }

    }

    public PossibleAction getActionTag() {
        return actionTag;
    }
}
