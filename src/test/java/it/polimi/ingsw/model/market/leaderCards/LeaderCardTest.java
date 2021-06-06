package it.polimi.ingsw.model.market.leaderCards;

import it.polimi.ingsw.model.player.RealPlayerBoard;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {

    List<LeaderCard> testDeck;

    @BeforeEach
    void setUp() {
        testDeck = LeaderCardsDeckBuilder.deckBuilder();
    }

    @Test
    void setActiveTest() {
        //Arrange
        LeaderCard discount = null;
        LeaderCard extraInventory = null;
        ExtraProductionLeaderCard extraProd = null;
        LeaderCard exchange = null;
        RealPlayerBoard rpb = new RealPlayerBoard("test");

        for(LeaderCard l : testDeck) {
            if(l instanceof DiscountLeaderCard) discount = l;
            if(l instanceof MarbleExchangeLeaderCard) exchange = l;
            if(l instanceof ExtraInventoryLeaderCard) extraInventory = l;
            if(l instanceof ExtraProductionLeaderCard) extraProd = (ExtraProductionLeaderCard) l;
        }

        //Act
        assert discount != null;
        discount.setActive(rpb);
        assert exchange != null;
        exchange.setActive(rpb);
        assert extraInventory != null;
        extraInventory.setActive(rpb);
        extraProd.setActive(rpb);

        //Assert
        ExtraProductionLeaderCard finalExtraProd = extraProd;
        assertAll(
                () -> assertEquals(rpb.getInventoryManager().getDiscount().size(), 1),
                () -> assertEquals(rpb.getInventoryManager().getExchange().size(), 1),
                () -> assertEquals(rpb.getInventoryManager().getWarehouse().getShelves().size(), 4),
                () -> assertEquals(rpb.getProductionBoard().getLeaderProductions().size(), 1),
                () -> assertNotNull(finalExtraProd.getInputResources()),
                () -> assertNotNull(finalExtraProd.getOutputResources()),
                () -> assertNull(finalExtraProd.getRequirements())
        );

        //Act
        extraProd.setSelected(false);
        assertFalse(extraProd.getSelected());
    }
}