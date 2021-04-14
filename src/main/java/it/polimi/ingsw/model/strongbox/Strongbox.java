package it.polimi.ingsw.model.strongbox;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.model.utilities.ResourceTag;

import java.util.HashMap;
import java.util.Map;

public class Strongbox {
    Map<ResourceType, Integer> strongbox;

    /**
     * Strongbox initialization, it always holds all the elements
     */
    public Strongbox (){
        this.strongbox = new HashMap<>();
        strongbox.put(ResourceType.COIN, 0);
        strongbox.put(ResourceType.STONE,0);
        strongbox.put(ResourceType.SHIELD,0);
        strongbox.put(ResourceType.SERVANT,0);
    }

    /**
     *
     * @param input -- resource to be added in the strongbox, it is only of type COIN, STONE, SERVANT or SHIELD
     */
    public void addResource(ResourceType input) {
        strongbox.put(input, strongbox.get(input)+1);
    }

    /**
     *
     * @param input -- resource to be added in the strongbox, it is only of type COIN, STONE, SERVANT or SHIELD
     * @param quantity -- quantity to be added
     */
    public void addResource(ResourceType input, int quantity) {
        strongbox.put(input, strongbox.get(input)+quantity);
    }

    /**
     *
     * @param tag - type and quantity of resource that needs to be removed
     * @throws CannotRemoveResourceException -- exception that states that the request wasn't either
     *                                          partially or completely satisfied
     */
    public void removeResource(ResourceTag tag) throws CannotRemoveResourceException {
        int quantity = tag.getQuantity();
        while (strongbox.get(tag.getType()) > 0 && quantity > 0){
            strongbox.put(tag.getType(), strongbox.get(tag.getType())-1);
            quantity--;
        }
        if(quantity > 0) throw new CannotRemoveResourceException(tag.getType(), quantity);
    }

    /**
     *
     * @return -- returns a dictionary with each entry being a type of resource and the quantity stored
     *            in the strongbox
     */
    public Map<ResourceType, Integer> getInventory(){
        return this.strongbox;
    }
}
