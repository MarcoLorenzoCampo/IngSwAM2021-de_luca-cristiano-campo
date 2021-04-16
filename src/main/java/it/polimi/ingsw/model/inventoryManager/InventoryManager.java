package it.polimi.ingsw.model.inventoryManager;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.model.strongbox.Strongbox;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.model.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    private ArrayList<MaterialResource> buffer;
    private Warehouse warehouse;
    private Strongbox strongbox;
    //private int penalty;
    private Map<ResourceType, Integer> inventory;
    private ArrayList<ResourceType> exchange;
    private ArrayList<ResourceType> discount;

    public InventoryManager(){
        buffer = new ArrayList<>();
        warehouse = new Warehouse();
        strongbox = new Strongbox();
        //penalty = 0;
        inventory = new HashMap<>();
        inventory.put(ResourceType.COIN, 0);
        inventory.put(ResourceType.STONE,0);
        inventory.put(ResourceType.SHIELD,0);
        inventory.put(ResourceType.SERVANT,0);
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
    }


    /**
     *
     * @param index -- removes resource buffer[index] from buffer once it had been placed in the warehouse, whether
     *                 this action was successful or not
     */
    public void removeFromBuffer(int index){
        buffer.remove(buffer.get(index));
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
    }


    /**
     *
     * @param input -- resource to be added to the strongbox
     */
    public void addResourceToStrongbox(ResourceType input){
        strongbox.addResource(input);
        inventory.put(input, inventory.get(input)+1);
    }


    /**
     *
     * @param price -- list of tags created when the player decides which productions to activate,
     *                 this list considers all the productions as a whole, thus having one price list
     *                 containing the sum of all single prices
     */
    public void discountPrice(List<ResourceTag> price){
        if(discount.size() != 0){
            for (ResourceTag iterator : price) {
                for (ResourceType discountIterator: discount) {
                    if(iterator.getType().equals(discountIterator)){
                        iterator.setQuantity(iterator.getQuantity()-1);
                    }
                }
            }
        }
    }


    /**
     *
     * @param discountedPrice -- price of whole production generated from discountedPriceMethod
     */
    public void updateRemoveInventory(List<ResourceTag> discountedPrice){
        for (ResourceTag iterator : discountedPrice) {
            inventory.put(iterator.getType(), (inventory.get(iterator.getType()))-iterator.getQuantity());
        }
    }


    /**
     *
     * @param priceOneResource -- type and quantity of resource to be removed from warehouse
     * @throws CannotRemoveResourceException -- thrown when warehouse couldn't remove all the quantity sated in
     *                                          priceOneResource
     */
    public void removeFromWarehouse(ResourceTag priceOneResource) throws CannotRemoveResourceException {
        warehouse.removeResources(priceOneResource);
    }


    /**
     *
     * @param priceOneResource -- type and quantity of resource to be removed from strongbox
     * @throws CannotRemoveResourceException -- thrown when strongbox couldn't remove all the quantity sated in
     *                                          priceOneResource
     */
    public void removeFromStrongbox(ResourceTag priceOneResource) throws CannotRemoveResourceException{
        strongbox.removeResource(priceOneResource);
    }

    public void deposit(MaterialResource materialResource) {
        buffer.add(materialResource);
    }
}
