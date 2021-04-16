package it.polimi.ingsw.model.resourceManager;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utilities.MaterialResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the inventory manager. Each test must refer to a player
 * since the inventory existence depends on the current player set by the game.
 */
class InventoryManagerTest {

    Player testPlayer;
    InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("testPlayer");
        Game.getGameInstance().setCurrentPlayer(testPlayer);
        inventoryManager = testPlayer.getInventoryManager();
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
    @Disabled
    void inventoryTest() {
    }

    @Test
    void addDiscountLeader() {
    }

    @Test
    void addExchangeLeader() {
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