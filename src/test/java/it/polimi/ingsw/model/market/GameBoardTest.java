package it.polimi.ingsw.model.market;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameBoardTest {

    @Test
    void getInstanceTest() throws FileNotFoundException {
        assertNotNull(GameBoard.getInstance());
    }


    @Test
    void allEntitiesNotNullTest() {
        assertAll(
                () -> assertNotNull(GameBoard.getInstance().getProductionCardMarket()),
                () -> assertNotNull(GameBoard.getInstance().getResourceMarket())
        );
    }
}