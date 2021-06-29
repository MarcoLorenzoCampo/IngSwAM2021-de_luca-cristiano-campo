package it.polimi.ingsw.model.inventoryManager;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.strongbox.Strongbox;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.model.warehouse.Shelf;
import it.polimi.ingsw.model.warehouse.Warehouse;
import it.polimi.ingsw.network.eventHandlers.Observable;
import it.polimi.ingsw.network.messages.serverMessages.BufferMessage;
import it.polimi.ingsw.network.messages.serverMessages.StrongboxMessage;
import it.polimi.ingsw.network.messages.serverMessages.WarehouseMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryManager extends Observable {

    private ArrayList<MaterialResource> buffer;
    private final Warehouse warehouse;
    private final Strongbox strongbox;
    private final Map<ResourceType, Integer> inventory;
    private final ArrayList<ResourceType> exchange;
    private final ArrayList<ResourceType> discount;

    private ArrayList<ResourceTag> toBeRemoved;

    public InventoryManager(){
        buffer = new ArrayList<>();
        warehouse = new Warehouse();
        strongbox = new Strongbox();
        inventory = new HashMap<>();
        inventory.put(ResourceType.COIN, 100);
        inventory.put(ResourceType.STONE,100);
        inventory.put(ResourceType.SHIELD,100);
        inventory.put(ResourceType.SERVANT,100);
        exchange = new ArrayList<>();
        discount =new ArrayList<>();
    }

    public ArrayList<MaterialResource> getBuffer() {
        return buffer;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public ArrayList<ResourceType> getExchange() {
        return exchange;
    }

    public ArrayList<ResourceType> getDiscount() {
        return discount;
    }

    public Map<ResourceType, Integer> getInventory() {
        return inventory;
    }

    /**
     * method invoked before deciding which marble to place first, if the exchange list is empty or contains
     * only one type then all white marbles are either deleted or changed
     */
    public void whiteMarblesExchange() {
        if (exchange.isEmpty()){
            buffer = (ArrayList<MaterialResource>) buffer.stream()
                    .filter(materialResource -> !materialResource.getResourceType().equals(ResourceType.UNDEFINED))
                    .collect(Collectors.toList());
        }

        else if (exchange.size() == 1) {
            for (MaterialResource iterator : buffer) {
                if(iterator.getResourceType().equals(ResourceType.UNDEFINED)) iterator.setResourceType(exchange.get(0));
            }
        }
        notifyObserver(messageUpdate());
    }

    public void customExchange(int index, ResourceType type){
        if(exchange.contains(type)) {
            buffer.get(index).setResourceType(type);
        }
        notifyObserver(messageUpdate());
    }

    /**
     *
     * @param effect -- type of discount to be applied, stated in the leader card of type discount
     */
    public void addDiscountLeader(ResourceType effect){
        discount.add(effect);
    }

    /**
     *
     * @param effect -- possible type in which a undefined resource can turn once obtained from the market
     *                  stated in the leader card of type exchange marble
     */
    public void addExchangeLeader (ResourceType effect){
        exchange.add(effect);
        if(exchange.size() == 2) {
            PlayingGame.getGameInstance().getCurrentPlayer().getPlayerState().setHasTwoExchange(true);
        }
    }

    /**
     *
     * @param index -- removes resource buffer[index] from buffer once it had been placed in the warehouse, whether
     *                 this action was successful or not
     */
    public void removeFromBuffer(int index){
        buffer.remove(buffer.get(index));
        notifyObserver(messageUpdate());
    }

    /**
     *
     * @param index -- places resource buffer[index] into warehouse
     * @throws DiscardResourceException -- exception thrown from warehouse when input resource cannot be placed
     */
    public void addResourceToWarehouse (int index) throws DiscardResourceException{
        ResourceType key = buffer.get(index).getResourceType();
        warehouse.addResource(buffer.get(index));
        inventory.put(key, inventory.get(key) + 1);
        removeFromBuffer(index);
        updateInventory();
    }

    /**
     *adds all the resources from the buffer to the strongbox
     *
     */
    public void addResourceToStrongbox(){
        for (MaterialResource iterator: buffer) {
            strongbox.addResource(iterator.getResourceType());
            inventory.put(iterator.getResourceType(), inventory.get(iterator.getResourceType())+1);
        }
        buffer.clear();
        updateInventory();
    }

    /**
     *
     * @param price -- list of tags created when the player decides which productions to activate,
     *                 this list considers all the productions as a whole, thus having one price list
     *                 containing the sum of all single prices
     */
    public void applyDiscount(List<ResourceTag> price){
        if(discount.size() != 0) {
            for (ResourceTag iterator : price) {
                for (ResourceType discountIterator: discount) {
                    if(iterator.getType().equals(discountIterator)){
                        iterator.setQuantity(iterator.getQuantity()-1);
                    }
                }
            }
        }
    }

    ///**
    // *
    // * @param discountedPrice -- price of whole production generated from discountedPriceMethod
    // */
    /*public void updateRemoveInventory(List<ResourceTag> discountedPrice){
        for (ResourceTag iterator : discountedPrice) {
            inventory.put(iterator.getType(), (inventory.get(iterator.getType()))-iterator.getQuantity());
        }
    }

     */

    /**
     *
     * @param priceOneResource -- type and quantity of resource to be removed from warehouse
     * @throws CannotRemoveResourceException -- thrown when warehouse couldn't remove all the quantity sated in
     *                                          priceOneResource
     */
    public void removeFromWarehouse(ResourceTag priceOneResource) throws CannotRemoveResourceException {
        warehouse.removeResources(priceOneResource);
        updateInventory();
    }

    private void updateInventory() {
        Map<ResourceType, Integer> temp = new HashMap<>();
        for (ResourceType iterator:strongbox.getInventory().keySet()) {
            temp.put(iterator, strongbox.getInventory().get(iterator));
        }

        for (Shelf iterator: warehouse.getShelves()) {
            if(!iterator.getType().equals(ResourceType.UNDEFINED))
            temp.put(iterator.getType(), temp.get(iterator.getType()) + iterator.getQuantity());
        }

        for (ResourceType iterator : temp.keySet()){
            inventory.put(iterator, temp.get(iterator));
        }
        notifyObserver(new StrongboxMessage(strongbox.getInventory()));
        notifyObserver(new WarehouseMessage(warehouse));
    }

    /**
     * @param priceOneResource -- type and quantity of resource to be removed from strongbox
     * @throws CannotRemoveResourceException -- thrown when strongbox couldn't remove all the quantity sated in
     *                                          priceOneResource
     */
    public void removeFromStrongbox(ResourceTag priceOneResource) throws CannotRemoveResourceException{
        strongbox.removeResource(priceOneResource);
        updateInventory();
    }

    /**
     * When the player is disconnected, his buffer gets emptied.
     */
    public void resetBuffer() {
        buffer.clear();
    }

    public void deposit(MaterialResource materialResource) {
        buffer.add(materialResource);
    }

    public BufferMessage messageUpdate(){
        return new BufferMessage(buffer);
    }

    /**
     * Method to remove resources after buying a card or activating productions if the player
     * gets disconnected.
     *
     * Removing from warehouse is prioritized.
     *
     * @param toBeRemoved: resources to remove.
     */
    public void defaultRemove(ArrayList<ResourceTag> toBeRemoved) {
        for(ResourceTag resourceTag : toBeRemoved) {
            try {
                warehouse.removeResources(resourceTag);
            } catch (CannotRemoveResourceException e) {
                try {
                    strongbox.removeResource(new ResourceTag(e.getType(), e.getQuantity()));
                } catch (CannotRemoveResourceException ignored) { }
            }
        }
    }

    public ArrayList<ResourceTag> getToBeRemoved() {
        return toBeRemoved;
    }

    public void setToBeRemoved(ArrayList<ResourceTag> toBeRemoved) {
        this.toBeRemoved = toBeRemoved;
    }
}
