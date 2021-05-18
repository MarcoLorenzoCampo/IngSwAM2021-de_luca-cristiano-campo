package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RealPlayerTest {

    RealPlayer testRealPlayer;

    @BeforeEach
    void setUp() {
        testRealPlayer = new RealPlayer("testRealPlayer");
    }

    @Test
    void gettersAndSettersTest() {
        //Arrange
        int victoryPoints = 3;

        //Act
        testRealPlayer.setVictoryPoints(victoryPoints);

        //Assert
        assertAll(
                () -> assertEquals(testRealPlayer.getVictoryPoints(), victoryPoints),
                () -> assertNotNull(testRealPlayer.getPlayerBoard().getInventoryManager()),
                () -> assertNotNull(testRealPlayer.getPlayerState()),
                () -> assertNotNull(testRealPlayer.getPlayerBoard()),
                () -> assertNotNull(testRealPlayer.getName())
        );
    }

    @Test
    void setFirstToPlay() {
        //Act
        testRealPlayer.setFirstToPlay();

        //Assert
        assertTrue(testRealPlayer.getIsFirstToPlay());
    }

    /**
     * Testing different playerstates.
     */
    @Test
    void basicPlayerStateTest() {
        //Arrange
        PlayerState basicState;

        //Act
        basicState = testRealPlayer.getPlayerState();

        //Assert
        assertAll(
                () -> assertTrue(basicState.getHasPlaceableLeaders()),
                () -> assertFalse(basicState.getHasBoughCard()),
                () -> assertFalse(basicState.getHasPickedResources()),
                () -> assertTrue(basicState.getHasPlaceableLeaders())
        );
    }

    @Test
    void exclusiveActionPerformedPlayerState() {
        //Arrange
        PlayerState performedActionState;

        //Act
        performedActionState = testRealPlayer.getPlayerState();

        performedActionState.performedExclusiveAction();

        //Assert
        assertAll(
                () -> assertTrue(performedActionState.getHasPickedResources()),
                () -> assertTrue(performedActionState.getHasBoughCard()),
                () -> assertTrue(performedActionState.getHasPlaceableLeaders())
        );
    }
}