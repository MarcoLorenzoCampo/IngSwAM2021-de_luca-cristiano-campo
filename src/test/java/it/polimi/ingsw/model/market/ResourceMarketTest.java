package it.polimi.ingsw.model.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Marco Lorenzo Campo
 */
class ResourceMarketTest {

    private ResourceMarket resourceMarket;

    @BeforeEach
    void init() {
        try {
            resourceMarket = new ResourceMarket();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        positiveBoundBreaker = resourceMarket.getResourceBoard().size()
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
        int acceptedBound;

        //Act
        acceptedBound = new Random().nextInt(resourceMarket.getResourceBoard().size());

        //Assert
        assertDoesNotThrow(
                () -> resourceMarket.pickResources(acceptedBound)
        );
    }
}