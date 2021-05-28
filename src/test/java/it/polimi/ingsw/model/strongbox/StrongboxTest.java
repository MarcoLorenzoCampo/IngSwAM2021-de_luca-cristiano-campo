package it.polimi.ingsw.model.strongbox;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.model.utilities.ResourceTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class StrongboxTest {
    Strongbox strongbox;
    
    @BeforeEach
    void setStrongbox(){ strongbox = new Strongbox(); }

    @Test
    void addResourceTest(){
        //Arrange
        ResourceType input = ResourceType.COIN;

        //Act
        strongbox.addResource(input);
        strongbox.addResource(input);

        //Assert
        assertEquals(2, (int) strongbox.getInventory().get(ResourceType.COIN));
    }

    @Disabled
    @Test
    void removeResourceAllowedTest(){
        //Arrange
        ResourceType input = ResourceType.COIN;
        ResourceTag toBeRemoved = new ResourceTag(ResourceType.COIN, 1);

        //Act
        strongbox.addResource(input);
        Assertions.assertDoesNotThrow(() -> strongbox.removeResource(toBeRemoved));

        //Assert
        assertEquals(0, (int) strongbox.getInventory().get(ResourceType.COIN));
    }

    @Disabled
    @Test
    void removeResourceNotAllowedTest(){
        //Arrange
        ResourceType input = ResourceType.COIN;
        ResourceTag toBeRemoved = new ResourceTag(ResourceType.COIN, 2);

        //Act
        strongbox.addResource(input);
        CannotRemoveResourceException exception = Assertions.assertThrows(CannotRemoveResourceException.class,
                ()-> strongbox.removeResource(toBeRemoved));

        //Assert
        assertEquals(0, (int) strongbox.getInventory().get(ResourceType.COIN));
        assertEquals(ResourceType.COIN, exception.getType());
        assertEquals(1, exception.getQuantity());
    }

    @Test
    void readInventoryTest(){
        //Arrange
        Map<ResourceType, Integer> toBeRead;

        //Act
        toBeRead = strongbox.getInventory();

        //Assert
        Assertions.assertEquals(toBeRead.entrySet(), strongbox.getInventory().entrySet());
    }

}
