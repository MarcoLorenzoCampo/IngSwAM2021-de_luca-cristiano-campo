package it.polimi.ingsw.actions;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.MultiplayerGame;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertAll;

class ActionTest {

    @Mock
    Player testPlayer;
    @Mock
    MultiplayerGame testMultiplayerGame;



    @BeforeEach
    void setUp() {
        testPlayer = new Player("testPlayer");
        testMultiplayerGame = MultiplayerGame.getGameInstance();
        testMultiplayerGame.setCurrentPlayer(testPlayer);
    }

    @Disabled
    @Test
    void getResourceFromMarketActionTest()
            throws InvalidPlayerException, NoMatchingRequisitesException,
            InvalidGameStateException, EndTurnException, LeaderCardException,
            GetResourceFromMarketException, BuyCardFromMarketException {

        //Arrange
        Action getResourceFromMarketAction;

        //Act
        getResourceFromMarketAction =
                new GetResourceFromMarketAction("testPlayer", 1);
        testMultiplayerGame.getCurrentPlayer().getPlayerBoard().getAction(getResourceFromMarketAction);

        //Assert
        assertAll();
    }


}