package it.polimi.ingsw.actions;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.NameTakenException;
import it.polimi.ingsw.exceptions.NoMorePlayersException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing basic actions.
 */
class AcceptedConditionsForActionsTest {

    GameManager gameManager;
    ActionManager actionManager;
    String actionSender;
    IGame currentGame;

    @BeforeEach
    void setUp() throws NameTakenException, NoMorePlayersException {
        resetSingleton();
        gameManager = new GameManager(2);
        actionManager = gameManager.getActionManager();
        gameManager.getLobbyManager().addNewPlayer("testPlayer");
        gameManager.getLobbyManager().setPlayingOrder();
        actionSender = gameManager.getCurrentGame().getCurrentPlayer().getName();
        currentGame = gameManager.getCurrentGame();
        currentGame.getCurrentState().setGameState(PossibleGameStates.GAME_STARTED);
    }

    @Test
    void acceptAllActionsForFreshPlayer() {
        //Arrange
        DiscardLeaderCardAction testDiscardAction;
        GetResourceFromMarketAction testGetAction;
        PlaceLeaderAction testPlaceLeader;
        int initialCards =
                gameManager.getCurrentGame().getCurrentPlayer().getOwnedLeaderCards().size();

        //Act
        testDiscardAction = new DiscardLeaderCardAction(actionSender, 0, currentGame);
        testGetAction = new GetResourceFromMarketAction(actionSender, 0, currentGame);
        testPlaceLeader = new PlaceLeaderAction(actionSender, 2, currentGame);

        //Assert
        assertAll(
                () -> assertDoesNotThrow(
                        () -> actionManager.actionReceiver(testDiscardAction)),
                () -> assertDoesNotThrow(
                        () -> actionManager.actionReceiver(testGetAction)),
                /* card shouldn't be places because it does not satisfy the requirements */
                () -> assertDoesNotThrow(
                        () -> actionManager.actionReceiver(testPlaceLeader)),
                () -> assertEquals(initialCards-1,
                        currentGame.getCurrentPlayer().getOwnedLeaderCards().size())
        );
    }

    @Test
    void buyCardFromMarket() {
        //Arrange
        BuyProductionCardAction testBuyCard;
        RealPlayer customPlayer = new RealPlayer("customScenarioPlayer");
        gameManager.getCurrentGame().setCurrentPlayer(customPlayer);
        ProductionCard toAdd = new ProductionCard(Level.ONE, Color.BLUE, 0,
                        new ArrayList<>());

        //Act
        testBuyCard =
                new BuyProductionCardAction("customScenarioPlayer", toAdd, 0, currentGame);

        //Assert
        assertDoesNotThrow(() -> actionManager.actionReceiver(testBuyCard));
    }

    void resetSingleton() {
        IGame game = PlayingGame.getGameInstance();

        game.setCurrentPlayer(null);
        game.getCurrentState().setGameState(PossibleGameStates.SETUP);
    }
}