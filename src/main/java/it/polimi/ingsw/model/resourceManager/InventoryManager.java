package it.polimi.ingsw.model.resourceManager;

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
    private int penalty;
    private Map<ResourceType, Integer> inventory;
    private ArrayList<ResourceType> exchange;
    private ArrayList<ResourceType> discount;

    public InventoryManager(){
        buffer = new ArrayList<>();
        warehouse = new Warehouse();
        strongbox = new Strongbox();
        penalty = 0;
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

    public int getPenalty() {
        return penalty;
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

    public void deposit (MaterialResource input){
        buffer.add(input);
    }

    public void addDiscountLeader(ResourceType effect){
        discount.add(effect);
    }

    public void addExchangeLeader (ResourceType effect){
        exchange.add(effect);
    }

    public void removeFromBuffer(int index){
        buffer.remove(buffer.get(index));
    }

    public void addResourceToWarehouse (int index) throws DiscardResourceException{
        ResourceType key = buffer.get(index).getResourceType();
        warehouse.addResource(buffer.get(index));
        inventory.put(key, inventory.get(key) + 1);
    }

    public void addResourceToStrongbox(ResourceType input){
        strongbox.addResource(input);
        inventory.put(input, inventory.get(input)+1);
    }

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
    public void updateRemoveInventory(List<ResourceTag> discountedPrice){
        for (ResourceTag iterator : discountedPrice) {
            inventory.put(iterator.getType(), (inventory.get(iterator.getType()))-iterator.getQuantity());
        }
    }

    public void removeFromWarehouse(ResourceTag priceOneResource) throws CannotRemoveResourceException {
        warehouse.removeResources(priceOneResource);
    }

    public void removeFromStrongbox(ResourceTag priceOneResource) throws CannotRemoveResourceException{
        strongbox.removeResource(priceOneResource);
    }

}
