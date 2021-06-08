package it.polimi.ingsw.model.utilities.builders;

import it.polimi.ingsw.model.token.LorenzoTokenPile;
import it.polimi.ingsw.model.token.TokenDiscard;
import it.polimi.ingsw.model.token.TokenMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoTokensBuilderTest {

    LorenzoTokenPile pileUnderTest;

    @BeforeEach
    void setUp() {
        pileUnderTest = new LorenzoTokenPile();
    }

    @Test
    void buildTest() {
        //Arrange
        int numberOfDiscards = 4;
        int moves = 3;
        int foundDiscards = 0;
        int foundMoves = 0;

        //Act
        for(int i=0; i<pileUnderTest.getLorenzoTokens().size(); i++) {
            if(pileUnderTest.getLorenzoTokens().get(i) instanceof TokenDiscard)
                foundDiscards++;
            if(pileUnderTest.getLorenzoTokens().get(i) instanceof TokenMove)
                foundMoves++;
        }

        //Assert
        int finalFoundDiscards = foundDiscards;
        int finalFoundMoves = foundMoves;
        assertAll(
                () -> assertEquals(finalFoundDiscards, numberOfDiscards),
                () -> assertEquals(finalFoundMoves, moves)
        );
    }

}