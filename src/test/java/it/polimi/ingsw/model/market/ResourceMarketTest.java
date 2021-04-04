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
        System.out.println(resourceMarket.getResourceBoard().size());
    }

    @Test
    void pickResourcesTest() {

        int boundsBreaker = resourceMarket.getResourceBoard().size()
                + new Random().nextInt();

        resourceMarket.pickResources(0);
        assertAll(
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> resourceMarket.pickResources(boundsBreaker))
        );
    }
}