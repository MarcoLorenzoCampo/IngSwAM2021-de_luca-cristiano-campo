package it.polimi.ingsw.model.productionBoard;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.utilities.BaseProduction;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.model.utilities.builders.ResourceBuilder;

import java.util.*;

public class ProductionBoard {
    private ProductionSlot[] productionSlots  ;
    private ArrayList<BaseProduction> leaderProductions;
    private Map<Color, int[]> inventory;
    private int victoryPoints;
    private BaseProduction finalProduction;
    private boolean baseProductionSelected;

    public ProductionBoard(){
        productionSlots = new ProductionSlot[3];
        productionSlots[0] = new ProductionSlot();
        productionSlots[1] = new ProductionSlot();
        productionSlots[2] = new ProductionSlot();
        leaderProductions = new ArrayList<>();
        inventory = new HashMap<>();
        inventory.put(Color.PURPLE, new int[4]);
        inventory.put(Color.GREEN, new int[4]);
        inventory.put(Color.YELLOW, new int[4]);
        inventory.put(Color.BLUE, new int[4]);
        victoryPoints = 0;
        finalProduction = new BaseProduction();
        finalProduction.setOutputResources( new ArrayList<>());
        finalProduction.setInputResources(new ArrayList<>());
    }

    public ArrayList<BaseProduction> getLeaderProductions() {
        return leaderProductions;
    }

    public BaseProduction getFinalProduction() {
        return finalProduction;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<Color, int[]> getInventory() {
        return inventory;
    }

    public ProductionSlot[] getProductionSlots() {
        return productionSlots;
    }


    public boolean checkRequirements(DevelopmentTag[] requirements){
        for (DevelopmentTag iterator : requirements) {
            if(inventory.get(iterator.getColor())[iterator.getLevel().ordinal()] < iterator.getQuantity())
                return false;
        }
        return true;
    }

    /**
     *
     * @param index -- index of slot in which to put new production card
     * @param newProductionCard -- production card acquired from production market
     */
    public void placeProductionCard(int index, ProductionCard newProductionCard){
        productionSlots[index].setProductionCard(newProductionCard);
        inventory.get(newProductionCard.getColor())[newProductionCard.getLevel().ordinal()]++;
        inventory.get(newProductionCard.getColor())[Level.ANY.ordinal()]++;
        victoryPoints = victoryPoints + newProductionCard.getVictoryPoints();
    }

    /**
     *
     * @param index -- index of slot in which to put new production card
     * @param newProductionCard -- production card acquired from production market
     * @return -- tells if place action is valid or not
     */
    public boolean checkPutCard(int index, ProductionCard newProductionCard){
        return productionSlots[index].canPlaceAnotherCard(newProductionCard);
    }

    /**
     *
     * @param destination -- states if the resource tag need to be added as an input or an output
     * @param input -- resource tag of chosen production that needs to be copied in final production
     */
    public void updateFinalProduction(ArrayList<ResourceTag> destination, ResourceTag input){
        boolean placed = false;
        if(destination.isEmpty()){
            destination.add(input);
        }
        else{
            for (ResourceTag iterator: destination) {
                if(iterator.getType().equals(input.getType())){
                    iterator.setQuantity(iterator.getQuantity()+input.getQuantity());
                    placed = true;
                }
            }
            if(!placed){
                destination.add(input);
            }
        }
    }

    /**
     *
     * @param input -- list of resource tags that form the input necessary to execute that leader production
     * @param output -- list of resource tags that form the output given from the leader production
     */
    public void addLeaderProduction(ResourceTag[] input, ResourceTag[] output){
        BaseProduction leaderProduction = new BaseProduction();
        ArrayList<ResourceTag> inputAsList= new ArrayList<>();
        ArrayList<ResourceTag> outputAsList= new ArrayList<>();
        Collections.addAll(inputAsList, input);
        Collections.addAll(outputAsList, output);
        leaderProduction.setInputResources(inputAsList);
        leaderProduction.setOutputResources(outputAsList);
        leaderProductions.add(leaderProduction);
    }

    /**
     *
     * @param input1 -- first resource involved in the input of the base production, chosen by player
     * @param input2 -- second resource involved in the input of the base production, chosen by player
     * @param output -- resource given by the base production, chosen by player
     */
    public void selectBaseProduction(ResourceType input1,ResourceType input2, ResourceType output){
        if(!baseProductionSelected) {
            updateFinalProduction(finalProduction.getInputResources(), new ResourceTag(input1, 1));
            updateFinalProduction(finalProduction.getInputResources(), new ResourceTag(input2, 1));
            updateFinalProduction(finalProduction.getOutputResources(), new ResourceTag(output, 1));
            baseProductionSelected = true;
        }
    }

    /**
     *
     * @param index -- index of production slot that holds the production card the player wants to activate
     */
    public void selectProductionSlot(int index){
        if(!productionSlots[index].isSelected()){
            for (ResourceTag iterator : productionSlots[index].getProductionCard().getInputResources()) {
                updateFinalProduction(finalProduction.getInputResources(), iterator);
            }
            for (ResourceTag iterator : productionSlots[index].getProductionCard().getOutputResources()) {
                updateFinalProduction(finalProduction.getOutputResources(), iterator);
            }
            productionSlots[index].setSelected(true);
        }
    }

    /**
     *
     * @param index -- index of the base production given by the leader card the player wants to activate
     * @param output1 -- resource that will be given in output, chosen by the player
     */
    public void selectLeaderProduction(int index, ResourceType output1){
        for (ResourceTag iterator: leaderProductions.get(index).getInputResources()) {
            updateFinalProduction(finalProduction.getInputResources(), iterator);
        }
        for (ResourceTag iterator : leaderProductions.get(index).getOutputResources()) {
            if(iterator.getType().equals(ResourceType.UNDEFINED)){
                updateFinalProduction(finalProduction.getOutputResources(), new ResourceTag(output1, 1));
            }
            else{
                updateFinalProduction(finalProduction.getOutputResources(), iterator);
            }
        }
    }

    /**
     *  method that resets the production process
     */
    public void clearSelection(){
        for (ProductionSlot iterator : productionSlots) {
            iterator.setSelected(false);
        }
        baseProductionSelected = false;
        finalProduction.getInputResources().clear();
        finalProduction.getOutputResources().clear();
    }

    /**
     *
     * @return -- list of resources based on the output of finalProduction
     */
    public LinkedList<Resource> executeProduction(){
        return ResourceBuilder.build(finalProduction.getOutputResources());
    }
}
