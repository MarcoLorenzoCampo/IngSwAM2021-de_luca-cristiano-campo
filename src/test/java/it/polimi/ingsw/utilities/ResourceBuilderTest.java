package it.polimi.ingsw.utilities;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.FaithResource;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.builders.ResourceBoardBuilder;
import it.polimi.ingsw.model.utilities.builders.ResourceBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceBuilderTest {

    ResourceBuilder resourceBuilder;
    ResourceBoardBuilder resourceBoardBuilder;

    @BeforeEach
    void setup() {
        resourceBuilder = new ResourceBuilder();
        resourceBoardBuilder = new ResourceBoardBuilder();
    }

    @Test
    void buildResourceTest() {

        //Arrange
        LinkedList<ResourceType> resourceTypes = new LinkedList<>();
        LinkedList<Resource> resourcesUnderTest;

        //Act
        resourceTypes.add(ResourceType.STONE);
        resourceTypes.add(ResourceType.FAITH);

        resourcesUnderTest = resourceBuilder.build(resourceTypes);

        //Assert
        assertAll(
                () -> assertInstanceOf(FaithResource.class, resourcesUnderTest.get(1)),
                () -> assertInstanceOf(MaterialResource.class, resourcesUnderTest.get(0))
        );
    }
}
