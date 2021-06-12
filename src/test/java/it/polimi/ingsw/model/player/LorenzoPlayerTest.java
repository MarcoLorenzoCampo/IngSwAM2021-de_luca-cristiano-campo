package it.polimi.ingsw.model.player;

import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.model.token.LorenzoTokenPile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoPlayerTest {

    private LorenzoPlayer lorenzoPlayer;
    private LorenzoTokenPile lorenzoTokenPile;

    @BeforeEach
    void setUp() {
        lorenzoPlayer = new LorenzoPlayer();
        lorenzoTokenPile = new LorenzoTokenPile();
    }

    @Test
    void lorenzoTest() {
        assertAll(
                () -> assertNotNull(lorenzoPlayer.getLorenzoPlayerBoard()),
                () -> assertEquals(lorenzoPlayer.getFaithPosition(), 0)
        );
    }

    @Test
    void lorenzoTokenTest() {
        for(int i=0; i<lorenzoTokenPile.getLorenzoTokens().size(); i++) {
            lorenzoPlayer.getLorenzoPlayerBoard().getAction(new LorenzoAction(lorenzoPlayer));
        }
    }
}