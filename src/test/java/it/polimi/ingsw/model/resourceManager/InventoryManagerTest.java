package it.polimi.ingsw.model.resourceManager;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.MaterialResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

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
        inventoryManager = testRealPlayer.getInventoryManager();
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
            materialResource.deposit();
        }

        //Assert
        assertAll(
                () -> assertEquals(inventoryManager.getBuffer().size(),
                        toBeAdded.size()),
                () -> assertNotNull(inventoryManager.getBuffer())
        );
    }

    @Test
    void inventoryTest() {
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
    }

    @Test
    void addResourceToWarehouse() {
    }

    @Test
    void addResourceToStrongbox() {
    }

    @Test
    void discountPrice() {
    }

    @Test
    void updateRemoveInventory() {
    }

    @Test
    void removeFromWarehouse() {
    }

    @Test
    void removeFromStrongbox() {
    }
}