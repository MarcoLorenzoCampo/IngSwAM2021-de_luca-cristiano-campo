package it.polimi.ingsw.actions;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ActionTest {

    Player testPlayer;
    Game testGame;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("testPlayer");
        testGame = Game.getGameInstance();
        testGame.getCurrentState().setGameState(PossibleGameStates.GAME_STARTED);
        testGame.setCurrentPlayer(testPlayer);
        testPlayer.getPlayerState().endTurnReset();
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

        //Assert
        assertAll(
        );
    }


}