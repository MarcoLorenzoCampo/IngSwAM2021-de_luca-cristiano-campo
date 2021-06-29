package it.polimi.ingsw.model.ProductionBoard;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.ProductionCardMarket;
import it.polimi.ingsw.model.market.leaderCards.ExtraProductionLeaderCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import it.polimi.ingsw.model.utilities.BaseProduction;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    @Test
    void PlaceProductionCard(){
        //Arrange
        ArrayList<ProductionCard> available_1 =
                (ArrayList<ProductionCard>) productionCardMarket.getAvailableCards()
                .stream()
                .filter(productionCard -> productionCard.getLevel().equals(Level.ONE))
                .collect(Collectors.toList());

        ArrayList<ProductionCard> available_2 =
                (ArrayList<ProductionCard>) productionCardMarket.getAvailableCards()
                        .stream()
                        .filter(productionCard -> productionCard.getLevel().equals(Level.TWO))
                        .collect(Collectors.toList());


        boolean canPutCard;
        boolean cannotPutCard;
        int indexOfChosenCard = 0;
        int indexOfChosenSlot = 2;
        ProductionCard chosen;
        ProductionCard chosen_level_2;

        //Act
        chosen = available_1.get(indexOfChosenCard);
        chosen_level_2 = available_2.get(indexOfChosenCard);
        cannotPutCard = productionBoard.checkPutCard(indexOfChosenSlot, chosen_level_2);
        canPutCard = productionBoard.checkPutCard(indexOfChosenSlot, chosen);
        productionBoard.placeProductionCard(indexOfChosenSlot, chosen);

        //Assert
        Assertions.assertAll(
                ()->Assertions.assertTrue(canPutCard),
                ()->Assertions.assertEquals(Level.ONE, productionBoard.getProductionSlots()[indexOfChosenSlot].getLevel()),
                ()->Assertions.assertEquals(1, productionBoard.getCardsInventory().get(chosen.getColor())[Level.ONE.ordinal()]),
                ()->Assertions.assertEquals(1, productionBoard.getCardsInventory().get(chosen.getColor())[Level.ANY.ordinal()]),
                ()->Assertions.assertNull(productionBoard.getProductionSlots()[0].getProductionCard()),
                ()->Assertions.assertNull(productionBoard.getProductionSlots()[1].getProductionCard()),
                ()->Assertions.assertFalse(cannotPutCard),
                ()->Assertions.assertEquals(productionBoard.getVictoryPoints(), chosen.getVictoryPoints())
        );
    }

    @Test
    void ActivateBaseProduction(){
        //Arrange
        ArrayList<ResourceType> wantedProduction = new ArrayList<>();

        wantedProduction.add(ResourceType.COIN);
        wantedProduction.add(ResourceType.SHIELD);
        wantedProduction.add(ResourceType.SERVANT);

        //Act
        productionBoard.selectBaseProduction(wantedProduction.get(0), wantedProduction.get(1), wantedProduction.get(2));

        //Assert
        Assertions.assertAll(
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction()),
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction().getInputResources()),
                ()->Assertions.assertEquals(2, productionBoard.getFinalProduction().getInputResources().size()),
                ()->Assertions.assertEquals(ResourceType.COIN, productionBoard.getFinalProduction().getInputResources().get(0).getType()),
                ()->Assertions.assertEquals(1, productionBoard.getFinalProduction().getInputResources().get(0).getQuantity()),
                ()->Assertions.assertEquals(ResourceType.SHIELD, productionBoard.getFinalProduction().getInputResources().get(1).getType()),
                ()->Assertions.assertEquals(1, productionBoard.getFinalProduction().getInputResources().get(1).getQuantity()),
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction().getOutputResources()),
                ()->Assertions.assertEquals(1, productionBoard.getFinalProduction().getOutputResources().size()),
                ()->Assertions.assertEquals(ResourceType.SERVANT, productionBoard.getFinalProduction().getOutputResources().get(0).getType()),
                ()->Assertions.assertEquals(1, productionBoard.getFinalProduction().getOutputResources().get(0).getQuantity()),
                ()->Assertions.assertTrue(productionBoard.isBaseProductionSelected())

        );

    }

    @Test
    void ActivateProduction(){
        //Arrange
        ProductionCard first = productionCardMarket.getAvailableCards().stream()
                .filter(productionCard -> productionCard.getLevel().equals(Level.ONE))
                .collect(Collectors.toList())
                .get(0);
        int chosen_slot = 0;
        boolean isSelected;

        //Act
        productionBoard.placeProductionCard(chosen_slot, first);
        productionBoard.selectProductionSlot(chosen_slot);
        isSelected = productionBoard.getProductionSlots()[0].isSelected();

        //Assert
        Assertions.assertAll(
                ()->Assertions.assertFalse(isSelected),
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction()),
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction().getInputResources()),
                ()->Assertions.assertEquals(first.getInputResources().size(), productionBoard.getFinalProduction().getInputResources().size()),
                ()->Assertions.assertEquals(first.getOutputResources().size(), productionBoard.getFinalProduction().getOutputResources().size())

        );
    }

    @Test
    void ActivateExtraProduction(){
        //Arrange
        LeaderCard extra_prod = LeaderCardsDeckBuilder.deckBuilder()
                .stream()
                .filter(leaderCard -> leaderCard.getEffectType().equals(EffectType.EXTRA_PRODUCTION))
                .collect(Collectors.toList())
                .get(0);
        int chosen = 0;
        ResourceType wanted = ResourceType.COIN;
        boolean isSelected;


        //Act
        productionBoard.addLeaderProduction((ExtraProductionLeaderCard) extra_prod);
        productionBoard.selectLeaderProduction(chosen, wanted);
        isSelected = productionBoard.getLeaderProductions().get(0).getSelected();

        //Assert
        Assertions.assertAll(
                ()->Assertions.assertTrue(isSelected),
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction()),
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction().getInputResources()),
                ()->Assertions.assertEquals(1, productionBoard.getFinalProduction().getInputResources().size()),
                ()->Assertions.assertEquals(((ExtraProductionLeaderCard) extra_prod).getOutputResources().length, productionBoard.getFinalProduction().getOutputResources().size()),
                ()->Assertions.assertEquals(extra_prod.getResource(), productionBoard.getFinalProduction().getInputResources().get(0).getType()),
                ()->Assertions.assertEquals(((ExtraProductionLeaderCard) extra_prod).getInputResources()[0].getQuantity(), productionBoard.getFinalProduction().getInputResources().get(0).getQuantity()),
                ()->Assertions.assertEquals(wanted, productionBoard.getFinalProduction().getOutputResources().get(0).getType()),
                ()->Assertions.assertEquals(1, productionBoard.getFinalProduction().getOutputResources().get(0).getQuantity()),
                ()->Assertions.assertEquals(ResourceType.FAITH, productionBoard.getFinalProduction().getOutputResources().get(1).getType()),
                ()->Assertions.assertEquals(1, productionBoard.getFinalProduction().getOutputResources().get(1).getQuantity())
        );

    }

    @Test
    void CompleteProductionTest(){
        //Arrange
        ProductionCard available = productionCardMarket.getAvailableCards()
                        .stream()
                        .filter(productionCard -> productionCard.getLevel().equals(Level.ONE))
                        .collect(Collectors.toList())
                        .get(0);
        int size_before_input;
        int size_before_output;

        //Act
        productionBoard.placeProductionCard(0,available);
        productionBoard.selectProductionSlot(0);
        size_before_input = productionBoard.getFinalProduction().getInputResources().size();
        size_before_output = productionBoard.getFinalProduction().getOutputResources().size();
        productionBoard.clearSelection();

        //Assert
        Assertions.assertAll(
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction()),
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction().getInputResources()),
                ()->Assertions.assertNotNull(productionBoard.getFinalProduction().getOutputResources()),
                ()->Assertions.assertNotEquals(0, size_before_input),
                ()->Assertions.assertNotEquals(0, size_before_output),
                ()->Assertions.assertEquals(0, productionBoard.getFinalProduction().getInputResources().size()),
                ()->Assertions.assertEquals(0, productionBoard.getFinalProduction().getOutputResources().size())
        );
    }




}
