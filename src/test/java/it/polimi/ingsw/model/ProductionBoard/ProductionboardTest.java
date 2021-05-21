package it.polimi.ingsw.model.ProductionBoard;

import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductionboardTest {

    ProductionBoard productionBoard;
    ProductionCardMarket productionCardMarket;

    @BeforeEach
    void setProductionBoard() {
        productionBoard = new ProductionBoard();
        productionCardMarket = new ProductionCardMarket();
    }

    @Test
    void EmptyProductionSlots(){
        //Arrange

        //Act

        //Assert
        Assertions.assertAll(
                ()-> Assertions.assertNull(productionBoard.getProductionSlots()[0].getProductionCard()),
                ()-> Assertions.assertNull(productionBoard.getProductionSlots()[1].getProductionCard()),
                ()-> Assertions.assertNull(productionBoard.getProductionSlots()[2].getProductionCard())
        );
    }

    @Disabled
    @Test
    void PlaceProductionCard(){
        //Arrange
        ArrayList<ProductionCard> available =
                (ArrayList<ProductionCard>) productionCardMarket.getAvailableCards()
                .stream()
                .filter(productionCard -> productionCard.getLevel().equals(Level.ONE))
                .collect(Collectors.toList());
        boolean canPutCard;
        int indexOfChosenCard = 0;
        int indexOfChosenSlot = 2;
        ProductionCard chosen;

        //Act
        chosen = available.get(indexOfChosenCard);
        canPutCard = productionBoard.checkPutCard(indexOfChosenSlot, chosen);
        productionBoard.placeProductionCard(indexOfChosenSlot, chosen);

        //Assert
        Assertions.assertAll(
                ()->Assertions.assertTrue(canPutCard),
                ()->Assertions.assertEquals(Level.ONE, productionBoard.getProductionSlots()[indexOfChosenSlot].getLevel()),
                ()->Assertions.assertEquals(1, productionBoard.getCardsInventory().get(chosen.getColor())[Level.ONE.ordinal()]),
                ()->Assertions.assertEquals(1, productionBoard.getCardsInventory().get(chosen.getColor())[Level.ANY.ordinal()])
        );
    }
}
