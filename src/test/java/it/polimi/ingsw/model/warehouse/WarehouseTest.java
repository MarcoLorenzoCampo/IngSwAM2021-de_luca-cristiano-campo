package it.polimi.ingsw.model.warehouse;


import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class WarehouseTest {

    Warehouse warehouse;

    @BeforeEach
    void setup(){
        warehouse = new Warehouse();
    }

    @Test
    void addOneResource() {
        //Arrange
        MaterialResource input = new MaterialResource(ResourceType.COIN);

        //Act
        assertDoesNotThrow(()->warehouse.addResource(input));

        //Assert
        for (int i = 0; i < warehouse.getShelves().size()-1; i++) {
            if(i<2){
                assert(warehouse.getShelves().get(i).getType().equals(ResourceType.UNDEFINED));
            }
            else assert(warehouse.getShelves().get(i).getType().equals(ResourceType.COIN));
        }
        assertTrue(warehouse.isWarehouseValid());
        assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void OverFillOneSlot(){
        //Arrange
        MaterialResource input = new MaterialResource(ResourceType.COIN);

        //Act
        assertDoesNotThrow(()->warehouse.addResource(input));
        assertDoesNotThrow(()->warehouse.addResource(input));
        assertDoesNotThrow(()->warehouse.addResource(input));
        assertThrows(DiscardResourceException.class, ()->warehouse.addResource(input));
        assertTrue(warehouse.isWarehouseValid());
        assertEquals(3, warehouse.getShelves().size());
        assertEquals(ResourceType.COIN, warehouse.getShelves().get(2).getType());
        assertEquals(3, warehouse.getShelves().get(2).getQuantity());

    }

    @Test
    void fillWarehouseInOrder(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);
        MaterialResource thirdResource = new MaterialResource(ResourceType.SERVANT);

        //Act

        assertDoesNotThrow(()->warehouse.addResource(firstResource));
        assertDoesNotThrow(()->warehouse.addResource(firstResource));
        assertDoesNotThrow(()->warehouse.addResource(firstResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(thirdResource));


        //Assert
        assertTrue(warehouse.isWarehouseValid());
        assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void fillWarehouseWithSwaps(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);
        MaterialResource thirdResource = new MaterialResource(ResourceType.SERVANT);

        //Act
        assertDoesNotThrow(()->warehouse.addResource(firstResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(thirdResource));
        assertDoesNotThrow(()->warehouse.addResource(thirdResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));

        //Assert
        assertTrue(warehouse.isWarehouseValid());
        assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void noSlotsForAnotherType(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);
        MaterialResource thirdResource = new MaterialResource(ResourceType.SERVANT);
        MaterialResource fourthResource = new MaterialResource(ResourceType.STONE);

        //Act
        assertDoesNotThrow(()->warehouse.addResource(firstResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(thirdResource));
        Assertions.assertThrows(DiscardResourceException.class, ()->warehouse.addResource(fourthResource));
        assertTrue(warehouse.isWarehouseValid());
        assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void addExtraInventory(){
        //Arrange
        ResourceType extraInventoryType = ResourceType.COIN;

        //Act
        warehouse.addExtraInventory(extraInventoryType);

        //Assert
        assertTrue(warehouse.isWarehouseValid());
        assertEquals(4, warehouse.getShelves().size());
        assertEquals(ResourceType.COIN, warehouse.getShelves().get(3).getType());
    }

    @Test
    void overFillExtraSlotWithFreeSlots(){
        //Arrange
        MaterialResource input = new MaterialResource(ResourceType.COIN);
        ResourceType extraInventoryType = ResourceType.COIN;

        //Act
        warehouse.addExtraInventory(extraInventoryType);
        assertDoesNotThrow(()->warehouse.addResource(input));
        assertDoesNotThrow(()->warehouse.addResource(input));
        assertDoesNotThrow(()->warehouse.addResource(input));
        assertDoesNotThrow(()->warehouse.addResource(input));
        assertDoesNotThrow(()->warehouse.addResource(input));
        assertThrows(DiscardResourceException.class,()->warehouse.addResource(input));


        //Assert
        assertTrue(warehouse.isWarehouseValid());
        assertEquals(4, warehouse.getShelves().size());
        assertEquals(ResourceType.COIN, warehouse.getShelves().get(2).getType());
        assertEquals(3, warehouse.getShelves().get(2).getQuantity());
        assertEquals(ResourceType.COIN, warehouse.getShelves().get(3).getType());
        assertEquals(2, warehouse.getShelves().get(3).getQuantity());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void OverfillExtraSlotWithNoFreeSlots(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);
        MaterialResource thirdResource = new MaterialResource(ResourceType.SERVANT);
        MaterialResource fourthResource = new MaterialResource(ResourceType.STONE);
        ResourceType extraInventoryType = ResourceType.STONE;

        //Act
        assertDoesNotThrow(()->warehouse.addResource(firstResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(thirdResource));
        warehouse.addExtraInventory(extraInventoryType);
        assertDoesNotThrow(()->warehouse.addResource(fourthResource));
        assertDoesNotThrow(()->warehouse.addResource(fourthResource));
        assertThrows(DiscardResourceException.class, ()->warehouse.addResource(fourthResource));

        assertTrue(warehouse.isWarehouseValid());
        assertEquals(4, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void cannotSwapLasTwoSlots(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);

        //Act
        assertDoesNotThrow(()->warehouse.addResource(firstResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(secondResource));
        assertDoesNotThrow(()->warehouse.addResource(firstResource));
        assertThrows(DiscardResourceException.class, ()->warehouse.addResource(firstResource));

        assertTrue(warehouse.isWarehouseValid());
        assertEquals(3, warehouse.getShelves().size());

        /*for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }*/
    }


    @Test
    void RemoveAllResources(){
        //Arrange
        ResourceTag toBeRemoved1 = new ResourceTag(ResourceType.SHIELD, 1);
        ResourceTag toBeRemoved2 = new ResourceTag(ResourceType.SERVANT, 2);
        ResourceTag toBeRemoved3 = new ResourceTag(ResourceType.COIN, 3);

        //Act
        warehouse.getShelves().get(0).getElement().setType(ResourceType.SHIELD);
        warehouse.getShelves().get(0).getElement().setQuantity(1);
        warehouse.getShelves().get(1).getElement().setType(ResourceType.SERVANT);
        warehouse.getShelves().get(1).getElement().setQuantity(2);
        warehouse.getShelves().get(2).getElement().setType(ResourceType.COIN);
        warehouse.getShelves().get(2).getElement().setQuantity(3);

        //Assert
        assertTrue(warehouse.isWarehouseValid());
        assertEquals(3, warehouse.getShelves().size());

        assertDoesNotThrow(()->warehouse.removeResources(toBeRemoved1));
        assertDoesNotThrow(()->warehouse.removeResources(toBeRemoved2));
        assertDoesNotThrow(()->warehouse.removeResources(toBeRemoved3));

        assertTrue(warehouse.isWarehouseValid());
        assertEquals(3, warehouse.getShelves().size());
    }

    @Test
    void RemoveMoreResourcesThanAllowed(){
        //Arrange
        ResourceTag toBeRemoved = new ResourceTag(ResourceType.COIN, 4);
        MaterialResource foundResource = new MaterialResource(ResourceType.COIN);

        //Act
        for (int i = 0; i < 3; i++) {
            assertDoesNotThrow(() -> warehouse.addResource(foundResource));
        }
        CannotRemoveResourceException exception =
                assertThrows(CannotRemoveResourceException.class, ()->warehouse.removeResources(toBeRemoved));


        //Assert
        warehouse.isWarehouseValid();
        assertEquals(3, warehouse.getShelves().size());
        for (Shelf iterator: warehouse.getShelves()) {
            assertEquals(ResourceType.UNDEFINED, iterator.getType());
        }
        assertEquals(ResourceType.COIN, exception.getType());
        assertEquals(1, exception.getQuantity());

    }

    @Test
    void RemoveResourceNotPresent(){
        //Arrange
        ResourceTag toBeRemoved = new ResourceTag(ResourceType.COIN, 4);

        //Act
        assertThrows(CannotRemoveResourceException.class, ()->warehouse.removeResources(toBeRemoved));

        //Assert
        warehouse.isWarehouseValid();
        assertEquals(3, warehouse.getShelves().size());
    }
}
