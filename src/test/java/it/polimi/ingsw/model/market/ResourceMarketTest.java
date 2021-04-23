package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.game.MultiPlayerGame;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marco Lorenzo Campo
 */
class ResourceMarketTest {


    private ResourceMarket resourceMarket;

    @BeforeEach
    void setUp() {
        MultiPlayerGame.getGameInstance().setCurrentPlayer(new Player("TestPlayer"));
        resourceMarket = new ResourceMarket();
    }

    @Test
    void getMarketBoardTest() {
        assertNotNull(resourceMarket.getResourceBoard());
    }

    @Test
    void pickOutOfBoundsResourcesTest() {

        //Arrange
        int positiveBoundBreaker;
        int negativeBoundBreaker;

        //Act
        positiveBoundBreaker = resourceMarket.getResourceBoard().length
                + new Random().nextInt();
        negativeBoundBreaker = - (new Random().nextInt());

        //Assert
        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> resourceMarket.pickResources(positiveBoundBreaker)),

                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> resourceMarket.pickResources(negativeBoundBreaker))
        );
    }

    @Test
    void pickInBoundsResourcesTest() {

        //Arrange
        int acceptedBoundColumn;
        int acceptedBoundRow;

        //Act
        acceptedBoundColumn = 1;    //new Random().nextInt(resourceMarket.getResourceBoard().length);
        acceptedBoundRow = 5;   //new Random().nextInt()

        //Assert
        assertAll(
                () -> assertDoesNotThrow(
                        () -> resourceMarket.pickResources(acceptedBoundColumn)),
                () -> assertDoesNotThrow(
                        () -> resourceMarket.pickResources(acceptedBoundRow))
        );
    }
}