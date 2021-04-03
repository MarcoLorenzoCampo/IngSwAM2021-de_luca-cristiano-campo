package it.polimi.ingsw.parsers;


import org.junit.jupiter.api.*;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


class ResourceMarketParserTest {

    ResourceMarketParser resourceMarketParser = new ResourceMarketParser();

    @Test
    void parseResourceBoardContent() throws FileNotFoundException {
        assertNotNull(resourceMarketParser.parseResourceMarketContent());
    }

    @Test
    void parseResourceBoardDimensions() throws FileNotFoundException {
        assertNotNull(resourceMarketParser.parseResourceMarketDimensions());
    }
}