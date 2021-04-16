package it.polimi.ingsw.model.ProductionBoard;

import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductionboardTest {

    ProductionBoard productionBoard;
    ProductionCardMarket productionCardMarket;

    @BeforeEach
    void setProductionBoard(){
        productionBoard = new ProductionBoard();
        try {
            productionCardMarket = new ProductionCardMarket();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
                ()->Assertions.assertEquals(1, productionBoard.getInventory().get(chosen.getColor())[Level.ONE.ordinal()]),
                ()->Assertions.assertEquals(1, productionBoard.getInventory().get(chosen.getColor())[Level.ANY.ordinal()])
        );
    }

    @Test
    void placeProductionCardAboveOther(){
        //Arrange
        ArrayList<ProductionCard> available1 =
                (ArrayList<ProductionCard>) productionCardMarket.getAvailableCards()
                        .stream()
                        .filter(productionCard -> productionCard.getLevel().equals(Level.ONE))
                        .collect(Collectors.toList());
        ArrayList<ProductionCard> available2 =
                (ArrayList<ProductionCard>) productionCardMarket.getAvailableCards()
                .stream()
                .filter(productionCard -> productionCard.getLevel().equals(Level.TWO))
                .collect(Collectors.toList());
        boolean canPutCard1;
        boolean canPutCard2;
        int indexOfChosenCard =0;
        int indexOfChosenSlot =0;
        ProductionCard chosen1;
        ProductionCard chosen2;

        //Act
        chosen1 = available1.get(indexOfChosenCard);
        canPutCard1 = productionBoard.checkPutCard(indexOfChosenSlot, chosen1);
        productionBoard.placeProductionCard(indexOfChosenSlot,chosen1);

        chosen2 = available2.get(indexOfChosenCard);
        canPutCard2 = productionBoard.checkPutCard(indexOfChosenSlot, chosen2);
        productionBoard.placeProductionCard(indexOfChosenSlot,chosen2);

        //Assert
        Assertions.assertAll(
                ()->Assertions.assertTrue(canPutCard1),
                ()->Assertions.assertTrue(canPutCard2),
                ()->Assertions.assertEquals(chosen2.getLevel(), productionBoard.getProductionSlots()[indexOfChosenSlot].getLevel()),
                ()->Assertions.assertEquals(chosen2.getColor(), productionBoard.getProductionSlots()[indexOfChosenSlot].getProductionCard().getColor())
        );
    }


}
