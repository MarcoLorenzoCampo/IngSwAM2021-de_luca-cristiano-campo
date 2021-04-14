package it.polimi.ingsw.model.utilities.builders;

import it.polimi.ingsw.model.market.leaderCards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardsDeckBuilderTest {

    List<LeaderCard> deckUnderTest;

    @BeforeEach
    void setUp() {
        deckUnderTest = LeaderCardsDeckBuilder.deckBuilder();
    }

    /**
     * Testing the generated deck is made of the correct number and types
     * of leader cards.
     */
    @Test
    void leaderDeckPropertiesTest() {

        //Arrange
        int expectedCardsPerType;

        //Act
        expectedCardsPerType = 4;

        //Assert
        assertAll(
                () -> assertEquals(expectedCardsPerType,
                        deckUnderTest.stream().filter(c -> c instanceof DiscountLeaderCard).count()),
                () -> assertEquals(expectedCardsPerType,
                deckUnderTest.stream().filter(c -> c instanceof ExtraInventoryLeaderCard).count()),
                () -> assertEquals(expectedCardsPerType,
                deckUnderTest.stream().filter(c -> c instanceof MarbleExchangeLeaderCard).count()),
                () -> assertEquals(expectedCardsPerType,
                deckUnderTest.stream().filter(c -> c instanceof ExtraProductionLeaderCard).count())
        );

    }
}