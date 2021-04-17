package it.polimi.ingsw.model.ProductionBoard;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.market.leaderCards.ExtraProductionLeaderCard;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductionboardTest {

    ProductionBoard productionBoard;
    ProductionCardMarket productionCardMarket;
    ArrayList<ProductionCard> available1;
    ArrayList<ProductionCard> available2;
    ArrayList<ProductionCard> available3;
    ExtraProductionLeaderCard leaderProduction;

    @BeforeEach
    void setProductionBoard(){
        productionBoard = new ProductionBoard();
        try {
            productionCardMarket = new ProductionCardMarket();
            available1 =
                    (ArrayList<ProductionCard>) productionCardMarket.getAvailableCards()
                            .stream()
                            .filter(productionCard -> productionCard.getLevel().equals(Level.ONE))
                            .collect(Collectors.toList());
            available2 =
                    (ArrayList<ProductionCard>) productionCardMarket.getAvailableCards()
                            .stream()
                            .filter(productionCard -> productionCard.getLevel().equals(Level.TWO))
                            .collect(Collectors.toList());
            available3 =
                    (ArrayList<ProductionCard>) productionCardMarket.getAvailableCards()
                            .stream()
                            .filter(productionCard -> productionCard.getLevel().equals(Level.THREE))
                            .collect(Collectors.toList());

            DevelopmentTag[] requirements = new DevelopmentTag[1];
            ResourceTag[] inputresources = new ResourceTag[1];
            ResourceTag[] outputresources = new ResourceTag[2];
            requirements[0] = new DevelopmentTag(1, Color.GREEN, Level.TWO);
            inputresources[0] = new ResourceTag(ResourceType.COIN, 1);
            outputresources[0] = new ResourceTag(ResourceType.FAITH, 1);
            outputresources[1] = new ResourceTag(ResourceType.UNDEFINED, 1);

            leaderProduction =
                    new ExtraProductionLeaderCard
                            (4, EffectType.EXTRA_PRODUCTION,
                            requirements,inputresources,outputresources);



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
        boolean canPutCard;
        int indexOfChosenCard = 0;
        int indexOfChosenSlot = 2;
        ProductionCard chosen;

        //Act
        chosen = available1.get(indexOfChosenCard);
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
    void placeOnTopOfAnother() {
        //Arrange
        ProductionCard chosenLevelOne1;
        ProductionCard chosenLevelOne2;
        ProductionCard chosenLevelOne3;
        ProductionCard chosenLevelTwo1;
        ProductionCard chosenLevelThree1;
        boolean placeOneOnZero;
        boolean placeOneOnTwo;
        boolean placeTwoOnOne;
        boolean placeThreeOnOne;
        boolean placeThreeOnTwo;

        //Act
        chosenLevelOne1 = available1.get(0);
        chosenLevelOne2 = available1.get(1);
        chosenLevelOne3 = available1.get(2);
        chosenLevelTwo1 = available2.get(0);
        chosenLevelThree1 = available3.get(0);

        placeOneOnZero = productionBoard.checkPutCard(0, chosenLevelOne1);
        productionBoard.placeProductionCard(0, chosenLevelOne1);

        placeTwoOnOne = productionBoard.checkPutCard(0, chosenLevelTwo1);
        productionBoard.placeProductionCard(0, chosenLevelTwo1);

        placeOneOnTwo = productionBoard.checkPutCard(0, chosenLevelOne2);
        productionBoard.placeProductionCard(1, chosenLevelOne2);
        productionBoard.placeProductionCard(2, chosenLevelOne3);

        placeThreeOnOne = productionBoard.checkPutCard(1, chosenLevelThree1);
        placeThreeOnTwo = productionBoard.checkPutCard(0, chosenLevelThree1);
        productionBoard.placeProductionCard(0, chosenLevelThree1);


        //Assert
        Assertions.assertAll(
                () -> Assertions.assertTrue(placeOneOnZero),
                () -> Assertions.assertTrue(placeTwoOnOne),
                () -> Assertions.assertTrue(placeThreeOnTwo),
                () -> Assertions.assertFalse(placeOneOnTwo),
                () -> Assertions.assertFalse(placeThreeOnOne),

                () -> Assertions.assertEquals(chosenLevelThree1.getLevel(),
                        productionBoard.getProductionSlots()[0].getLevel()),
                () -> Assertions.assertEquals(chosenLevelThree1.getColor(),
                        productionBoard.getProductionSlots()[0].getProductionCard().getColor()),

                () -> Assertions.assertEquals(chosenLevelOne2.getLevel(),
                        productionBoard.getProductionSlots()[1].getLevel()),
                () -> Assertions.assertEquals(chosenLevelOne2.getColor(),
                        productionBoard.getProductionSlots()[1].getProductionCard().getColor()),

                () -> Assertions.assertEquals(chosenLevelOne3.getLevel(),
                        productionBoard.getProductionSlots()[2].getLevel()),
                () -> Assertions.assertEquals(chosenLevelOne3.getColor(),
                        productionBoard.getProductionSlots()[2].getProductionCard().getColor())
        );
    }

    @Test
    void addProductionLeaderTest(){
        //Arrange


        //Act
        productionBoard.addLeaderProduction(leaderProduction.getInputResources(), leaderProduction.getOutputResources());

        //Assert
        Assertions.assertAll(
                ()-> Assertions.assertEquals(1, productionBoard.getLeaderProductions().size()),
                ()-> Assertions.assertEquals(1, productionBoard.getLeaderProductions().get(0).getInputResources().size()),
                ()-> Assertions.assertEquals(2, productionBoard.getLeaderProductions().get(0).getOutputResources().size()),

                ()-> Assertions.assertEquals(ResourceType.COIN, productionBoard.getLeaderProductions().get(0).getInputResources().get(0).getType()),
                ()-> Assertions.assertEquals(1, productionBoard.getLeaderProductions().get(0).getInputResources().get(0).getQuantity()),

                ()-> Assertions.assertEquals(ResourceType.FAITH, productionBoard.getLeaderProductions().get(0).getOutputResources().get(0).getType()),
                ()-> Assertions.assertEquals(1, productionBoard.getLeaderProductions().get(0).getOutputResources().get(0).getQuantity()),

                ()-> Assertions.assertEquals(ResourceType.UNDEFINED, productionBoard.getLeaderProductions().get(0).getOutputResources().get(1).getType()),
                ()-> Assertions.assertEquals(1, productionBoard.getLeaderProductions().get(0).getOutputResources().get(1).getQuantity())
        );
    }


}
