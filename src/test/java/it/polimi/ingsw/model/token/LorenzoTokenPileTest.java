package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.player.LorenzoPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing lorenzo token properties.
 */
class LorenzoTokenPileTest {

    LorenzoTokenPile lorenzoTokenPile;

    @BeforeEach
    void setUp() {
        lorenzoTokenPile = new LorenzoTokenPile();
    }

    @Test
    void getTokensTest() {
        assertNotNull(lorenzoTokenPile.getLorenzoTokens());
    }
}