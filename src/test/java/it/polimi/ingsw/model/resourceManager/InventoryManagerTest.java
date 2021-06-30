package it.polimi.ingsw.model.resourceManager;


import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the inventory manager. Each test must refer to a player
 * since the inventory existence depends on the current player set by the game.
 */
class InventoryManagerTest {

    @Mock
    RealPlayer testRealPlayer;
    InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        testRealPlayer = new RealPlayer("testRealPlayer");
        PlayingGame.getGameInstance().setCurrentPlayer(testRealPlayer);
        inventoryManager = testRealPlayer.getPlayerBoard().getInventoryManager();
        testRealPlayer.getPlayerBoard().getInventoryManager().getStrongbox().getInventory().put(ResourceType.COIN, 0);
        testRealPlayer.getPlayerBoard().getInventoryManager().getStrongbox().getInventory().put(ResourceType.STONE,0);
        testRealPlayer.getPlayerBoard().getInventoryManager().getStrongbox().getInventory().put(ResourceType.SHIELD,0);
        testRealPlayer.getPlayerBoard().getInventoryManager().getStrongbox().getInventory().put(ResourceType.SERVANT,0);
    }

    @Test
    void depositTest() {
        //Arrange
        List<MaterialResource> toBeAdded;

        //Act
        toBeAdded = new LinkedList<>();
        toBeAdded.add(new MaterialResource(ResourceType.COIN));
        toBeAdded.add(new MaterialResource(ResourceType.SERVANT));

        for (MaterialResource materialResource : toBeAdded) {
            materialResource.deposit(testRealPlayer.getPlayerBoard());
        }

        //Assert
        assertAll(
                () -> assertEquals(inventoryManager.getBuffer().size(),
                        toBeAdded.size()),
                () -> assertNotNull(inventoryManager.getBuffer())
        );
    }

    @Test
    void addDiscountLeaderTest() {
        //Arrange
        //Act
        inventoryManager.addDiscountLeader(ResourceType.COIN);
        inventoryManager.addDiscountLeader(ResourceType.SERVANT);

        //Assert
        assertAll(
                () -> assertNotNull(inventoryManager.getDiscount()),
                () -> assertEquals(ResourceType.COIN, inventoryManager.getDiscount().get(0)),
                () -> assertNotEquals(ResourceType.SHIELD, inventoryManager.getDiscount().get(1))
        );
    }

    @Test
    void addExchangeLeader() {
        //Arrange
        //Act
        inventoryManager.addExchangeLeader(ResourceType.COIN);
        inventoryManager.addExchangeLeader(ResourceType.SERVANT);

        //Assert
        assertAll(
                () -> assertNotNull(inventoryManager.getExchange()),
                () -> assertEquals(ResourceType.COIN, inventoryManager.getExchange().get(0)),
                () -> assertNotEquals(ResourceType.SHIELD, inventoryManager.getExchange().get(1))
        );
    }

    @Test
    void removeFromBuffer() {
        //Arrange
        inventoryManager.deposit(new MaterialResource(ResourceType.COIN));
        int old_size;

        //Act
        old_size = inventoryManager.getBuffer().size();
        inventoryManager.removeFromBuffer(0);

        //Assert
        assertAll(
                () -> assertNotEquals(0 , old_size),
                () -> assertEquals(0, inventoryManager.getBuffer().size())
        );
    }

    @Test
    void addResourceToWarehouse() {
        //Arrange
        inventoryManager.deposit(new MaterialResource(ResourceType.SHIELD));
        inventoryManager.deposit(new MaterialResource(ResourceType.SHIELD));
        inventoryManager.getInventory().put(ResourceType.SHIELD, 2);
        inventoryManager.getWarehouse().getShelves().get(2).setElement(new ResourceTag(ResourceType.SHIELD, 2));

        //Act
        assertDoesNotThrow(()-> inventoryManager.addResourceToWarehouse(0));
        DiscardResourceException exception = assertThrows(DiscardResourceException.class,  ()-> inventoryManager.addResourceToWarehouse(0));
        //simulating the action resolves the exception
        inventoryManager.removeFromBuffer(0);

        //Assert
        assertAll(
                () -> assertEquals(0, inventoryManager.getBuffer().size()),
                () -> assertEquals(3, inventoryManager.getInventory().get(ResourceType.SHIELD)),
                () -> assertEquals(3, inventoryManager.getWarehouse().getShelves().get(2).getQuantity())
        );
    }

    @Test
    void addResourceToStrongbox() {
        //Arrange
        inventoryManager.deposit(new MaterialResource(ResourceType.SHIELD));
        inventoryManager.deposit(new MaterialResource(ResourceType.SHIELD));

        //Act
        inventoryManager.addResourceToStrongbox();

        //Assert
        assertAll(
                () -> assertEquals(0, inventoryManager.getBuffer().size()),
                () -> assertEquals(2, inventoryManager.getInventory().get(ResourceType.SHIELD)),
                () -> assertEquals(2, inventoryManager.getStrongbox().getInventory().get(ResourceType.SHIELD))
        );


    }

    @Test
    void discountPrice() {
        //Arrange
        List<ResourceTag> price = new ArrayList<>();
        price.add(new ResourceTag(ResourceType.COIN, 3));
        price.add(new ResourceTag(ResourceType.SHIELD, 3));
        price.add(new ResourceTag(ResourceType.SERVANT, 3));

        //Act
        inventoryManager.addDiscountLeader(ResourceType.COIN);
        inventoryManager.addDiscountLeader(ResourceType.SHIELD);
        inventoryManager.applyDiscount(price);

        //Assert
        assertAll(
                () -> assertEquals(ResourceType.COIN, inventoryManager.getDiscount().get(0)),
                () -> assertEquals(2, price.get(0).getQuantity()),
                () -> assertEquals(ResourceType.COIN, price.get(0).getType()),
                () -> assertEquals(2, price.get(1).getQuantity()),
                () -> assertEquals(ResourceType.SHIELD, price.get(1).getType()),
                () -> assertEquals(3, price.get(2).getQuantity()),
                () -> assertEquals(ResourceType.SERVANT, price.get(2).getType())
        );

    }

    @Test
    void removeFromWarehouse() {
        //Arrange
        inventoryManager.getInventory().put(ResourceType.SHIELD, 2);
        inventoryManager.getWarehouse().getShelves().get(2).setElement(new ResourceTag(ResourceType.SHIELD, 2));

        //Act
        assertDoesNotThrow(()-> inventoryManager.removeFromWarehouse(new ResourceTag(ResourceType.SHIELD, 1)));
        CannotRemoveResourceException exception = assertThrows(CannotRemoveResourceException.class,  ()-> inventoryManager.removeFromWarehouse(new ResourceTag(ResourceType.SHIELD, 2)));

        //Assert
        assertAll(
                () -> assertFalse( inventoryManager.getWarehouse().checkPresence(ResourceType.SHIELD)),
                //no one calls the update when remove from warehouse isn't paired with the other
                () -> assertEquals(1, inventoryManager.getInventory().get(ResourceType.SHIELD)),
                () -> assertEquals(1, exception.getQuantity())
        );
    }

    @Test
    void removeFromStrongbox() {
        //Arrange
        inventoryManager.getStrongbox().getInventory().put(ResourceType.SHIELD, 2);
        inventoryManager.getInventory().put(ResourceType.SHIELD, 2);
        //Act
        assertDoesNotThrow(()-> inventoryManager.removeFromStrongbox(new ResourceTag(ResourceType.SHIELD, 1)));
        CannotRemoveResourceException exception = assertThrows(CannotRemoveResourceException.class,  ()-> inventoryManager.removeFromStrongbox(new ResourceTag(ResourceType.SHIELD, 2)));

        //Assert
        assertAll(
                () -> assertEquals(0,inventoryManager.getStrongbox().getInventory().get(ResourceType.SHIELD)),
                //no one calls the update when remove from warehouse isn't paired with the other
                () -> assertEquals(1, inventoryManager.getInventory().get(ResourceType.SHIELD)),
                () -> assertEquals(1, exception.getQuantity())
        );
    }

    @Test
    void DeleteWhite(){
        //Arrange
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.COIN));
        int pre_size;

        //Act
        pre_size = inventoryManager.getBuffer().size();
        inventoryManager.whiteMarblesExchange();

        //Assert
        assertAll(
                () -> assertNotEquals(pre_size, inventoryManager.getBuffer().size()),
                () -> assertEquals(1, inventoryManager.getBuffer().size()),
                () -> assertEquals(ResourceType.COIN, inventoryManager.getBuffer().get(0).getResourceType())
        );


    }

    @Test
    void ChangeOnlyOne(){
        //Arrange
        inventoryManager.addExchangeLeader(ResourceType.SHIELD);
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.COIN));
        int pre_size;

        //Act
        pre_size = inventoryManager.getBuffer().size();
        inventoryManager.whiteMarblesExchange();

        //Assert
        assertAll(
                () -> assertEquals(pre_size, inventoryManager.getBuffer().size()),
                () -> assertEquals(4, inventoryManager.getBuffer().size()),
                () -> assertEquals(ResourceType.SHIELD, inventoryManager.getBuffer().get(0).getResourceType()),
                () -> assertEquals(ResourceType.SHIELD, inventoryManager.getBuffer().get(1).getResourceType()),
                () -> assertEquals(ResourceType.SHIELD, inventoryManager.getBuffer().get(2).getResourceType()),
                () -> assertEquals(ResourceType.COIN, inventoryManager.getBuffer().get(3).getResourceType())
        );
    }

    @Test
    void ChangeChoice(){
        //Arrange
        inventoryManager.addExchangeLeader(ResourceType.SHIELD);
        inventoryManager.addExchangeLeader(ResourceType.COIN);
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.UNDEFINED));
        inventoryManager.deposit(new MaterialResource(ResourceType.COIN));
        int pre_size;

        //Act
        pre_size = inventoryManager.getBuffer().size();
        inventoryManager.customExchange(0, ResourceType.SHIELD);
        inventoryManager.customExchange(1, ResourceType.COIN);
        inventoryManager.customExchange(2, ResourceType.STONE);

        //Assert
        assertAll(
                () -> assertEquals(pre_size, inventoryManager.getBuffer().size()),
                () -> assertEquals(4, inventoryManager.getBuffer().size()),
                () -> assertEquals(ResourceType.SHIELD, inventoryManager.getBuffer().get(0).getResourceType()),
                () -> assertEquals(ResourceType.COIN, inventoryManager.getBuffer().get(1).getResourceType()),
                () -> assertEquals(ResourceType.UNDEFINED, inventoryManager.getBuffer().get(2).getResourceType()),
                () -> assertEquals(ResourceType.COIN, inventoryManager.getBuffer().get(3).getResourceType())
        );

    }
}