package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marco Lorenzo Campo
*/

class ProductionCardMarketTest {

    private ProductionCardMarket productionCardMarket;
    private List<ProductionCard> availableCards;

    @BeforeEach
    void setup() {
        try {
            productionCardMarket = new ProductionCardMarket();
            availableCards = productionCardMarket.getAvailableCards();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * testing the behavior when a specific (color, level) is missing
     */
    @Test
    void noMoreCardsTest() {

    }

    @Test
    void getAvailableCardsTest() {
        assertNotNull(availableCards);
    }

    /**
     * Testing available cards are generated correctly
     * Uses equalsColorLevel method {@link ProductionCard}
     */
    @Test
    void availableCardsPropertiesTest() {

        productionCardMarket.showAvailableCards();
        boolean noRepeated = true;
        for(int i=0; i < availableCards.toArray().length; i++) {
            for(int j=0; j < availableCards.toArray().length; j++) {

                if(i != j) {
                    if(availableCards.get(i)
                            .equalsColorLevel(availableCards.get(j))) noRepeated = false;
                }
            }
        }
        assertTrue(noRepeated);
        assertEquals(availableCards.toArray().length, 12);
    }

    /**
     * Testing bought cards are removed correctly from
     * available cards
     */
    @Test
    void removeCardTest() {

        /* buying a random card from the available ones */
        ProductionCard boughtCard = productionCardMarket
                .getAvailableCards()
                .get(new Random().nextInt(availableCards.toArray().length));

        productionCardMarket.buyCard(boughtCard);

        assertAll(
                /* removing from available cards */
                () -> assertFalse(productionCardMarket.getAvailableCards().contains(boughtCard)),

                /* a new card with the same level and color replaces the bought card */
                () -> assertTrue(
                        availableCards.stream().anyMatch(c -> c.equalsColorLevel(boughtCard)))
        );
    }
}