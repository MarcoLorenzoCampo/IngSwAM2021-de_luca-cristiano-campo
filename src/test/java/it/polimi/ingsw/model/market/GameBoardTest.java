package it.polimi.ingsw.model.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameBoardTest {

    GameBoard gameboard;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        gameboard = GameBoard.getGameBoardInstance();
    }

    @Test
    void getInstanceTest() throws FileNotFoundException {
        assertNotNull(GameBoard.getGameBoardInstance());
    }


    @Test
    void allEntitiesNotNullTest() {
        assertAll(
                () -> assertNotNull(GameBoard.getGameBoardInstance().getProductionCardMarket()),
                () -> assertNotNull(GameBoard.getGameBoardInstance().getResourceMarket())
        );
    }
}