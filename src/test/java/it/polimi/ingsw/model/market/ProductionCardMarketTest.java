package it.polimi.ingsw.model.market;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.player.RealPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductionCardMarketTest {

    private ProductionCardMarket productionCardMarket;
    PlayingGame testPlayingGame;

    @BeforeEach
    void setUp() {
        productionCardMarket = new ProductionCardMarket();
        RealPlayer testRealPlayer = new RealPlayer("UnderTest");
        testPlayingGame = PlayingGame.getGameInstance();
        PlayingGame.getGameInstance().setCurrentPlayer(testRealPlayer);
    }

    /**
     * testing the behavior when a specific Color is missing. Uses the lorenzoRemoves method because it's
     * a convenient way to remove and replace cards (and it has already been tested).
     */
    @Test
    void noMoreCardsOfSpecificColorAndLevelTest() {

        //Arrange
        Color chosenColor = Color.BLUE;

        //Act
        for(int i=0; i<12; i++) {
            productionCardMarket.lorenzoRemoves(chosenColor);
        }

        //Assert
        assertAll(
                () -> assertEquals((int) productionCardMarket.getAvailableCards().stream()
                                .filter(c -> c.getColor().equals(chosenColor)).count(), 0),
                () -> assertDoesNotThrow(() -> productionCardMarket.lorenzoRemoves(chosenColor)),
                () -> assertEquals(productionCardMarket.getAvailableCards().size(), 9)
        );
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
        Set<ProductionCard> distinctSet;

        //Act
        numberOfColors = Color.values().length;
        numberOfLevels = Level.values().length;
        distinctSet = new HashSet<>(productionCardMarket.getAvailableCards());

        //Assert
        assertAll(
                /* checks number of available cards (excluding the ANY level) */
                () -> assertEquals(productionCardMarket.getAvailableCards().toArray().length,
                        (numberOfLevels-1)*numberOfColors),
                /* checks (Level, Color) is different for every card*/
                () -> assertEquals(productionCardMarket.getAvailableCards().toArray().length,
                        distinctSet.toArray().length)
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

    @Test
    void cardsPropertiesTest() {

        //Arrange
        ProductionCard p1;
        ProductionCard p2;

        //Act
        p1 = productionCardMarket.getAvailableCards().get(0);
        p2 = productionCardMarket.getAvailableCards().get(0);

        //Assert
        assertAll(
                () -> assertEquals(p1, p2),
                () -> assertNotNull(p1.getRequirements())
        );
    }

    @Test
    void removeEveryCardTest() {
        while(productionCardMarket.getAvailableCards().size() != 0) {

            productionCardMarket.getAvailableCards().remove(0);
        }
    }
}