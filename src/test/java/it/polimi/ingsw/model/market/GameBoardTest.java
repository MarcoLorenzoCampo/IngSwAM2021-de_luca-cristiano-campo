package it.polimi.ingsw.model.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameBoardTest {

    @Mock
    GameBoard gameboard;

    @BeforeEach
    void setUp() {
        gameboard = GameBoard.getGameBoardInstance();
    }

    @Test
    void getInstanceTest() {
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