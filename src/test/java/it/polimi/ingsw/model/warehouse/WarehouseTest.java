package it.polimi.ingsw.model.warehouse;


import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class WarehouseTest {
    Warehouse warehouse;

    @BeforeEach
    void setup(){
        warehouse = new Warehouse();
    }

    @Test
    void addOneResourceTest() {
        //Arrange
        MaterialResource input = new MaterialResource(ResourceType.COIN);

        //Act
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));

        //Assert
        for (int i = 0; i < warehouse.getShelves().size()-1; i++) {
            if(i<2){
                assert(warehouse.getShelves().get(i).getType().equals(ResourceType.UNDEFINED));
            }
            else assert(warehouse.getShelves().get(i).getType().equals(ResourceType.COIN));
        }
        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void OverFillOneSlotTest(){
        //Arrange
        MaterialResource input = new MaterialResource(ResourceType.COIN);

        //Act
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));
        Assertions.assertThrows(DiscardResourceException.class, ()->warehouse.addResource(input));
        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(3, warehouse.getShelves().size());
        Assertions.assertEquals(ResourceType.COIN, warehouse.getShelves().get(2).getType());
        Assertions.assertEquals(3, warehouse.getShelves().get(2).getQuantity());

    }

    @Test
    void fillWarehouseInOrderTest(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);
        MaterialResource thirdResource = new MaterialResource(ResourceType.SERVANT);

        //Act

        Assertions.assertDoesNotThrow(()->warehouse.addResource(firstResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(firstResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(firstResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(thirdResource));


        //Assert
        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void fillWarehouseWithSwapsTest(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);
        MaterialResource thirdResource = new MaterialResource(ResourceType.SERVANT);

        //Act
        Assertions.assertDoesNotThrow(()->warehouse.addResource(firstResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(thirdResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(thirdResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));

        //Assert
        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void noSlotsForAnotherTypeTest(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);
        MaterialResource thirdResource = new MaterialResource(ResourceType.SERVANT);
        MaterialResource fourthResource = new MaterialResource(ResourceType.STONE);

        //Act
        Assertions.assertDoesNotThrow(()->warehouse.addResource(firstResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(thirdResource));
        Assertions.assertThrows(DiscardResourceException.class, ()->warehouse.addResource(fourthResource));
        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }


    }

    @Test
    void addExtraInventoryTest(){
        //Arrange
        ResourceType extraInventoryType = ResourceType.COIN;

        //Act
        warehouse.addExtraInventory(extraInventoryType);

        //Assert
        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(4, warehouse.getShelves().size());
        Assertions.assertEquals(ResourceType.COIN, warehouse.getShelves().get(3).getType());
    }

    @Test
    void overFillExtraSlotWithFreeSlotsTest(){
        //Arrange
        MaterialResource input = new MaterialResource(ResourceType.COIN);
        ResourceType extraInventoryType = ResourceType.COIN;

        //Act
        warehouse.addExtraInventory(extraInventoryType);
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(input));
        Assertions.assertThrows(DiscardResourceException.class,()->warehouse.addResource(input));


        //Assert
        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(4, warehouse.getShelves().size());
        Assertions.assertEquals(ResourceType.COIN, warehouse.getShelves().get(2).getType());
        Assertions.assertEquals(3, warehouse.getShelves().get(2).getQuantity());
        Assertions.assertEquals(ResourceType.COIN, warehouse.getShelves().get(3).getType());
        Assertions.assertEquals(2, warehouse.getShelves().get(3).getQuantity());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void OverfillExtraSlotWithNoFreeSlotsTest(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);
        MaterialResource thirdResource = new MaterialResource(ResourceType.SERVANT);
        MaterialResource fourthResource = new MaterialResource(ResourceType.STONE);
        ResourceType extraInventoryType = ResourceType.STONE;

        //Act
        Assertions.assertDoesNotThrow(()->warehouse.addResource(firstResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(thirdResource));
        warehouse.addExtraInventory(extraInventoryType);
        Assertions.assertDoesNotThrow(()->warehouse.addResource(fourthResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(fourthResource));
        Assertions.assertThrows(DiscardResourceException.class, ()->warehouse.addResource(fourthResource));

        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(4, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }

    @Test
    void cannotSwapLasTwoSlotsTest(){
        //Arrange
        MaterialResource firstResource = new MaterialResource(ResourceType.COIN);
        MaterialResource secondResource = new MaterialResource(ResourceType.SHIELD);

        //Act
        Assertions.assertDoesNotThrow(()->warehouse.addResource(firstResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(secondResource));
        Assertions.assertDoesNotThrow(()->warehouse.addResource(firstResource));
        Assertions.assertThrows(DiscardResourceException.class, ()->warehouse.addResource(firstResource));

        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(3, warehouse.getShelves().size());

        for (Shelf iterator: warehouse.getShelves()) {
            System.out.println(
                    iterator.getType() +" "+ iterator.getQuantity() +" "+iterator.getCapacity());
        }
    }


    @Test
    void RemoveAllResourcesTest(){
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
        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(3, warehouse.getShelves().size());

        Assertions.assertDoesNotThrow(()->warehouse.removeResources(toBeRemoved1));
        Assertions.assertDoesNotThrow(()->warehouse.removeResources(toBeRemoved2));
        Assertions.assertDoesNotThrow(()->warehouse.removeResources(toBeRemoved3));

        Assertions.assertTrue(warehouse.isWarehouseValid());
        Assertions.assertEquals(3, warehouse.getShelves().size());
    }

    @Test
    void RemoveMoreResourcesThanAllowedTest(){
        //Arrange
        ResourceTag toBeRemoved = new ResourceTag(ResourceType.COIN, 4);
        MaterialResource foundResource = new MaterialResource(ResourceType.COIN);

        //Act
        for (int i = 0; i < 3; i++) {
            Assertions.assertDoesNotThrow(() -> warehouse.addResource(foundResource));
        }
        CannotRemoveResourceException exception = Assertions.assertThrows(CannotRemoveResourceException.class, ()->warehouse.removeResources(toBeRemoved));


        //Assert
        warehouse.isWarehouseValid();
        Assertions.assertEquals(3, warehouse.getShelves().size());
        for (Shelf iterator: warehouse.getShelves()) {
            Assertions.assertEquals(ResourceType.UNDEFINED, iterator.getType());
        }
        Assertions.assertEquals(ResourceType.COIN, exception.getType());
        Assertions.assertEquals(1, exception.getQuantity());

    }

    @Test
    void RemoveResourceNotPresentTest(){
        //Arrange
        ResourceTag toBeRemoved = new ResourceTag(ResourceType.COIN, 4);

        //Act
        Assertions.assertThrows(CannotRemoveResourceException.class, ()->warehouse.removeResources(toBeRemoved));

        //Assert
        warehouse.isWarehouseValid();
        Assertions.assertEquals(3, warehouse.getShelves().size());
    }

}
