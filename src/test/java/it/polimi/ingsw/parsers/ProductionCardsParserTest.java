package it.polimi.ingsw.parsers;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductionCardsParserTest {

    ProductionCardsParser productionCardsParser = new ProductionCardsParser();

    /**
     * Test deck parsing process
     */
    @Test
    void parseProductionDeck() throws FileNotFoundException {
        assertNotNull(productionCardsParser.parseProductionDeck());
    }
}