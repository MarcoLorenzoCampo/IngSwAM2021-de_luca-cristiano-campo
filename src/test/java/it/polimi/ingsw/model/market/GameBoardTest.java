package it.polimi.ingsw.model.market;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

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