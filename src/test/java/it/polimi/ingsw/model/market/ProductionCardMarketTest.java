package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marco Lorenzo Campo
*/

class ProductionCardMarketTest {

    private ProductionCardMarket productionCardMarket;

    @BeforeEach
    void setup() {
        try {
            productionCardMarket = new ProductionCardMarket();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * testing the behavior when a specific Color is missing
     *
     */
    @Test
    void noMoreCardsOfSpecificColorTest() {
        //Arrange

        //Act

        //Assert
    }

    @Test
    void showAvailableCardsTest() {
        productionCardMarket.showAvailableCards();
    }

    @Test
    void getAvailableCardsTest() {
        assertNotNull(productionCardMarket.getAvailableCards());
    }

    /**
     * Testing available cards are generated correctly
     * Uses @Override HashCode
     * {@link ProductionCard}
     */
    @Test
    void availableCardsPropertiesTest() {

        //Arrange
        int numberOfLevels;
        int numberOfColors;
        Set<ProductionCard> s;

        //Act
        numberOfColors = Color.values().length;
        numberOfLevels = Level.values().length;
        s = new HashSet<>(productionCardMarket.getAvailableCards());

        //Assert
        assertAll(
                /* checks number of available cards */
                () -> assertEquals(productionCardMarket.getAvailableCards().toArray().length,
                        numberOfLevels*numberOfColors),
                /* checks every (Level, Color) is different */
                () -> assertEquals(productionCardMarket.getAvailableCards().toArray().length,
                        s.toArray().length)
        );
    }

    /**
     * Testing bought cards are removed correctly from
     * available cards
     */
    @Test
    void removeCardTest() {

        //Arrange
        /* buying a random card from the available ones */
        ProductionCard boughtCard = productionCardMarket
                .getAvailableCards()
                .get(new Random().nextInt(productionCardMarket.getAvailableCards()
                        .toArray().length));

        //Act
        productionCardMarket.buyCard(boughtCard);

        //Assert
        assertAll(
                /* removing from available cards */
                () -> assertFalse(productionCardMarket.getAvailableCards().contains(boughtCard)),

                /* a new card with the same level and color replaces the bought card */
                () -> assertTrue(
                        productionCardMarket.getAvailableCards().
                                stream().anyMatch(c -> c.equalsColorLevel(boughtCard)))
        );
    }
}