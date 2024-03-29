package it.polimi.ingsw.model.game;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for game instance.
 */
class PlayingGameTest {

    @Mock
    private PlayingGame currentGame;

    @BeforeEach
    void setUp() {
        resetSingleton();
        currentGame = PlayingGame.getGameInstance();
    }

    @Test
    void setCurrentPlayer() {
        //Arrange
        RealPlayer testPlayer;

        //Act
        testPlayer = new RealPlayer("testPlayer");
        currentGame.setCurrentPlayer(testPlayer);

        //Assert
        assertNotNull(currentGame.getCurrentPlayer());
    }

    @Test
    void getGameInstance() {

        //Assert
        assertNotNull(currentGame);
    }

    @Test
    void getCurrentState() {
        //Arrange
        currentGame.getCurrentState().setGameState(PossibleGameStates.SETUP);

        //Assert
        assertAll(
                () -> assertEquals(PossibleGameStates.SETUP, currentGame.getCurrentState().getGameState())
        );

        //Act
        currentGame.getCurrentState().setGameState(PossibleGameStates.GAME_STARTED);

        //Assert
        assertAll(
                () -> assertEquals(PossibleGameStates.GAME_STARTED, currentGame.getCurrentState().getGameState())
        );

    }

    @Test
    void getIGameBoard() {

        //Assert
        assertAll(
                () -> assertNotNull(currentGame.getGameBoard())
        );
    }

    @Test
    void setGameStateTest() {
        currentGame.setCurrentState(PossibleGameStates.PLAYING);

        assertEquals(currentGame.getCurrentState().getGameState(), PossibleGameStates.PLAYING);
    }

    @Test
    void getCardsMax() {
        assertEquals(currentGame.getMaxCardsBought(), 7);
    }

    void resetSingleton() {
        PlayingGame.terminate();
        IGame game = PlayingGame.getGameInstance();

        game.setCurrentPlayer(null);
        game.getCurrentState().setGameState(PossibleGameStates.SETUP);
    }
}