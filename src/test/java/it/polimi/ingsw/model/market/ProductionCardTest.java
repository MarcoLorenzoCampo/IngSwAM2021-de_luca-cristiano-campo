package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionCardTest {

    private ProductionCard p1;

    @BeforeEach
    void setUp() {
        p1 = new ProductionCard(Level.ONE, Color.BLUE, 0, 10, null);
    }

    @Test
    void constructorAndEqualsTest() {
        ProductionCard p2 = new ProductionCard(Level.ONE, Color.BLUE, 0, 10, null);
        String s = p2.toString();

        assertAll(
                () -> assertEquals(s, p1.toString()),
                () -> assertEquals(p1, p2)
        );
    }

    @Test
    void placeCardTest() {
        ProductionBoard pb = new ProductionBoard();

        p1.placeCard(0, p1, pb);

        assertEquals(pb.getProductionSlots().length, 3);
    }
}