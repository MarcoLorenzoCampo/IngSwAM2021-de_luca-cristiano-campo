package it.polimi.ingsw.model.utilities.builders;

import it.polimi.ingsw.enumerations.ResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceBoardBuilderTest {

    /**
     * Testing the creation of the resource board is correct.
     */
    @Test
    void buildTest() {

        //Arrange
        int[] dimensions = {2, 2};
        String[] exampleContent = {"FAITH", "UNDEFINED", "STONE", "UNDEFINED"};
        ResourceType[][] boardUnderTest = ResourceBoardBuilder.build(dimensions, exampleContent);

        //Act

        //Assert
        assertAll(
                () -> assertNotNull(boardUnderTest[0][0])
        );
    }
}