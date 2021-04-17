package it.polimi.ingsw.actions;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertAll;

class ActionTest {

    @Mock
    Player testPlayer;
    @Mock
    Game testGame;



    @BeforeEach
    void setUp() {
        testPlayer = new Player("testPlayer");
        testGame = Game.getGameInstance();
        testGame.setCurrentPlayer(testPlayer);
    }

    @Disabled
    @Test
    void getResourceFromMarketActionTest()
            throws InvalidPlayerException, NoMatchingRequisitesException,
            InvalidGameStateException, EndTurnException, LeaderCardException,
            GetResourceFromMarketException, BuyCardFromMarketException, EndGameException {

        //Arrange
        Action getResourceFromMarketAction;

        //Act
        getResourceFromMarketAction =
                new GetResourceFromMarketAction("testPlayer", 1);
        testGame.getCurrentPlayer().getPlayerBoard().getAction(getResourceFromMarketAction);

        //Assert
        assertAll();
    }


}