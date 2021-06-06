package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.player.RealPlayerBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ResourceMarketTest {

    private ResourceMarket resourceMarket;

    @BeforeEach
    void setUp() {
        PlayingGame.getGameInstance().setCurrentPlayer(new RealPlayer("TestPlayer"));
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
                        () -> resourceMarket.pickResources(positiveBoundBreaker, new RealPlayerBoard("test"))),

                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> resourceMarket.pickResources(negativeBoundBreaker, new RealPlayerBoard("test")))
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
                        () -> resourceMarket.pickResources(acceptedBoundColumn, new RealPlayerBoard("test"))),
                () -> assertDoesNotThrow(
                        () -> resourceMarket.pickResources(acceptedBoundRow, new RealPlayerBoard("test")))
        );
    }

    @Test
    void getExtraMarbleTest() {
        assertInstanceOf(ResourceType.class, resourceMarket.getExtraMarble());
    }
}