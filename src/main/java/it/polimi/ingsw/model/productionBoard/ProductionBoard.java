package it.polimi.ingsw.model.productionBoard;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.inventoryManager.InventoryManager;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.ExtraProductionLeaderCard;
import it.polimi.ingsw.model.player.RealPlayerBoard;
import it.polimi.ingsw.model.utilities.BaseProduction;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.model.utilities.builders.ResourceBuilder;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.ChosenProductionMessage;
import it.polimi.ingsw.network.messages.serverMessages.ProductionBoardMessage;

import java.util.*;

public class ProductionBoard extends Observable {
    private final ProductionSlot[] productionSlots  ;
    private final ArrayList<ExtraProductionLeaderCard> leaderProductions;
    private boolean baseProductionSelected;
    private final Map<Color, int[]> cardsInventory;
    private int victoryPoints;
    private final BaseProduction finalProduction;

    public ProductionBoard(){
        productionSlots = new ProductionSlot[3];
        productionSlots[0] = new ProductionSlot();
        productionSlots[1] = new ProductionSlot();
        productionSlots[2] = new ProductionSlot();
        leaderProductions = new ArrayList<>();
        cardsInventory = new HashMap<>();
        cardsInventory.put(Color.PURPLE, new int[4]);
        cardsInventory.put(Color.GREEN, new int[4]);
        cardsInventory.put(Color.YELLOW, new int[4]);
        cardsInventory.put(Color.BLUE, new int[4]);
        victoryPoints = 0;
        finalProduction = new BaseProduction();
        baseProductionSelected = false;
    }

    public ArrayList<ExtraProductionLeaderCard> getLeaderProductions() {
        return leaderProductions;
    }

    public BaseProduction getFinalProduction() {
        return finalProduction;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<Color, int[]> getCardsInventory() {
        return cardsInventory;
    }

    public ProductionSlot[] getProductionSlots() {
        return productionSlots;
    }

    public void setBaseProductionSelected(boolean baseProductionSelected) {
        this.baseProductionSelected = baseProductionSelected;
    }

    public boolean isBaseProductionSelected() {
        return baseProductionSelected;
    }

    /**
     * puts the new production card in the designated slot
     * @param index -- index of production slot
     * @param newProductionCard -- new production card obtained
     */
    public void placeProductionCard(int index, ProductionCard newProductionCard){
        productionSlots[index].setProductionCard(newProductionCard);
        cardsInventory.get(newProductionCard.getColor())[newProductionCard.getLevel().ordinal()]++;
        cardsInventory.get(newProductionCard.getColor())[Level.ANY.ordinal()]++;
        victoryPoints = victoryPoints + newProductionCard.getVictoryPoints();
        notifyObserver(new ProductionBoardMessage(this));
    }

    /**
     * checks if the card can be placed in the designated production slot
     * @param index -- index of production slot
     * @param newProductionCard -- new production card obtained
     * @return --
     */
    public boolean checkPutCard(int index, ProductionCard newProductionCard){
        return productionSlots[index].canPlaceAnotherCard(newProductionCard);
    }

    /**
     * each resource tag can either be in the input of the final production or the output, this method
     * updates the final production
     * @param destination -- destination of the update, either input or output
     * @param input -- resource tag that needs to be put in the final production
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
        sortFinalProduction();
    }

    private void sortFinalProduction() {
        finalProduction.getInputResources().sort(Comparator.comparingInt((ResourceTag tag) -> tag.getType().ordinal()));
        finalProduction.getOutputResources().sort(Comparator.comparingInt((ResourceTag tag) -> tag.getType().ordinal()));
    }

    /**
     * moves the leader production from the playerBoard to the productionBoard
     * @param leaderCard -- extra production leader card activated
     */
    public void addLeaderProduction(ExtraProductionLeaderCard leaderCard){
        this.leaderProductions.add(leaderCard);
    }

    /**
     *
     * @param input1 -- first input resource
     * @param input2 -- second input resource
     * @param output -- resource in output
     */
    public void selectBaseProduction(ResourceType input1,ResourceType input2, ResourceType output){
        if(!isBaseProductionSelected()) {
            updateFinalProduction(finalProduction.getInputResources(), new ResourceTag(input1, 1));
            updateFinalProduction(finalProduction.getInputResources(), new ResourceTag(input2, 1));
            updateFinalProduction(finalProduction.getOutputResources(), new ResourceTag(output, 1));
            setBaseProductionSelected(true);
            notifyObserver(new ChosenProductionMessage(finalProduction));
        }
    }

    /**
     *
     * @param index -- index of production slot that holds the production card selected
     */
    public void selectProductionSlot(int index){
        if(productionSlots[index].isSelected()){
            for (ResourceTag iterator : productionSlots[index].getProductionCard().getInputResources()) {
                updateFinalProduction(finalProduction.getInputResources(), new ResourceTag(iterator.getType(), iterator.getQuantity()));
            }
            for (ResourceTag iterator : productionSlots[index].getProductionCard().getOutputResources()) {
                updateFinalProduction(finalProduction.getOutputResources(), new ResourceTag(iterator.getType(), iterator.getQuantity()));
            }
            productionSlots[index].setSelected(true);
            notifyObserver(new ChosenProductionMessage(finalProduction));
        }
    }

    /**
     *
     * @param index -- index of leader card selected
     * @param output1 -- resource in output wanted
     */
    public void selectLeaderProduction(int index, ResourceType output1){
        if(!leaderProductions.get(index).getSelected()) {
            leaderProductions.get(index).setSelected(true);
            for (ResourceTag iterator : leaderProductions.get(index).getInputResources()) {
                updateFinalProduction(finalProduction.getInputResources(), new ResourceTag(iterator.getType(), iterator.getQuantity()));
            }
            for (ResourceTag iterator : leaderProductions.get(index).getOutputResources()) {
                if (iterator.getType().equals(ResourceType.UNDEFINED)) {
                    updateFinalProduction(finalProduction.getOutputResources(), new ResourceTag(output1, 1));
                } else {
                    updateFinalProduction(finalProduction.getOutputResources(), new ResourceTag(iterator.getType(), iterator.getQuantity()));
                }
            }
            notifyObserver(new ChosenProductionMessage(finalProduction));
        }
    }

    /**
     * method that resets the whole selection, can be invoked when the action is finished and the resources are put in
     * the strongbox or when the selection has ended but the player doesn't have enough resources to execute the production
     */
    public void clearSelection(){
        for (ProductionSlot iterator : productionSlots) {
            iterator.setSelected(false);
        }
        for (ExtraProductionLeaderCard iterator: leaderProductions) {
            iterator.setSelected(false);
        }
        setBaseProductionSelected(false);
        finalProduction.getInputResources().clear();
        finalProduction.getOutputResources().clear();
    }

    /**
     *
     *  generates the list of material resources
     */
    public void executeProduction(RealPlayerBoard playerBoard){
        LinkedList<Resource> obtained = ResourceBuilder.build(finalProduction.getOutputResources());
        for (Resource iterator:obtained) {
            iterator.deposit(playerBoard);
        }
    }

    /**
     * Method used by the player to see if the production can be executed or not
     *
     * @param inventory: player's complete inventory
     * @return: returs true if the production can be executed, otherwise false
     */
    public boolean validateFinalProduction(InventoryManager inventory){
        for (ResourceTag iterator: finalProduction.getInputResources()) {
            if(iterator.getQuantity() > inventory.getInventory().get(iterator.getType()))
                return false;
        }
        return true;
    }
}
