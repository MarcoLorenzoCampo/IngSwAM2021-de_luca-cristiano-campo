package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.io.FileNotFoundException;
import java.util.List;
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
     * testing the behavior when a specific color is missing
     * same behavior is to be expected when a level is missing
     */
    @Test
    void noMoreCards() {

    }

    @Test
    void getAvailableCardsTest() {
        assertNotNull(availableCards);
    }

    /**
     * Testing available cards are generated correctly
     */
    @Test
    void availableCardsPropertiesTest() {

        productionCardMarket.showAvailableCards();
        boolean noRepeated = true;
        for(int i=0; i < availableCards.toArray().length; i++) {
            for(int j=0; j < availableCards.toArray().length; j++) {

                if(i != j) {
                    if(availableCards.get(i).equals(availableCards.get(j))) noRepeated = false;
                }
            }
        }
        assertTrue(noRepeated);
    }

    /**
     * Testing bought cards are removed correctly from
     * available cards
     */
    @Test
    void removeCardTest() {

        ProductionCard boughtCard = productionCardMarket.getAvailableCards().get(0);
        assertNotNull(boughtCard);

        productionCardMarket.buyCard(boughtCard);

        /* removing from available cards */
        assertFalse(productionCardMarket.getAvailableCards().contains(boughtCard));
    }
}