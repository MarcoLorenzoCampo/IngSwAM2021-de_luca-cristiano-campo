package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("testPlayer");
    }

    @Test
    void gettersAndSettersTest() {
        //Arrange
        int victoryPoints = 3;

        //Act
        testPlayer.setVictoryPoints(victoryPoints);

        //Assert
        assertAll(
                () -> assertEquals(testPlayer.getVictoryPoints(), victoryPoints),
                () -> assertNotNull(testPlayer.getInventoryManager()),
                () -> assertNotNull(testPlayer.getPlayerState()),
                () -> assertNotNull(testPlayer.getPlayerBoard()),
                () -> assertNotNull(testPlayer.getName())
        );
    }

    @Test
    void setFirstToPlay() {
        //Act
        testPlayer.setFirstToPlay();

        //Assert
        assertTrue(testPlayer.getIsFirstToPlay());
    }

    /**
     * Testing different playerstates.
     */
    @Test
    void basicPlayerStateTest() {
        //Arrange
        PlayerState basicState;

        //Act
        basicState = testPlayer.getPlayerState();

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
        performedActionState = testPlayer.getPlayerState();

        performedActionState.performedExclusiveAction();

        //Assert
        assertAll(
                () -> assertTrue(performedActionState.getHasPickedResources()),
                () -> assertTrue(performedActionState.getHasBoughCard()),
                () -> assertTrue(performedActionState.getHasPlaceableLeaders())
        );
    }
}