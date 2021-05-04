package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.network.virtualView.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionsToRefuseActionsTest {

    private GameManager gameManager;
    private ActionManager actionManager;
    private String actionSender;
    private IGame currentGame;

    @BeforeEach
    void setUp() {
        resetSingleton();
        gameManager = new GameManager(2);
        actionManager = gameManager.getActionManager();
        gameManager.getLobbyManager().addNewPlayer("testPlayer", new VirtualView(null));
        gameManager.getLobbyManager().setPlayingOrder();
        actionSender = gameManager.getCurrentGame().getCurrentPlayer().getName();
        currentGame = gameManager.getCurrentGame();
        currentGame.getCurrentState().setGameState(PossibleGameStates.GAME_STARTED);
    }

    @Test
    void refuseBecausePlayerState() {
        //Arrange
        GetResourceFromMarketAction testGetAction;

        //Act
        currentGame.getCurrentPlayer().getPlayerState().performedExclusiveAction();

        testGetAction = new GetResourceFromMarketAction(actionSender, 0, currentGame);
        actionManager.actionReceiver(testGetAction);

        //Assert
        assertAll(
               () -> assertFalse(actionManager.isActionAccepted())
        );
    }

    @Test
    void refuseBecauseGameStatus() {
    }

    void resetSingleton() {
        IGame game = PlayingGame.getGameInstance();

        game.setCurrentPlayer(null);
        game.getCurrentState().setGameState(PossibleGameStates.SETUP);
    }
}