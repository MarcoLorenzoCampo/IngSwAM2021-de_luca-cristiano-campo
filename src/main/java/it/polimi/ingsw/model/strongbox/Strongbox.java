package it.polimi.ingsw.model.strongbox;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.CannotRemoveResourceException;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.eventHandlers.Observable;

import java.util.HashMap;
import java.util.Map;

public class Strongbox extends Observable {

    private final Map<ResourceType, Integer> strongbox;

    /**
     * Strongbox initialization, it always holds all the elements
     */
    public Strongbox (){
        this.strongbox = new HashMap<>();

        strongbox.put(ResourceType.COIN, 0);
        strongbox.put(ResourceType.STONE, 0);
        strongbox.put(ResourceType.SHIELD, 0);
        strongbox.put(ResourceType.SERVANT, 0);
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

    /**
     * Method to set the default strongbox resources for test purposes.
     * @param coin: number of coins;
     * @param shield: number of shields;
     * @param servant: number of servants;
     * @param stone: number of stones;
     */
    public void setTestInventory(int coin, int shield, int servant, int stone) {
        strongbox.put(ResourceType.COIN, coin);
        strongbox.put(ResourceType.SHIELD, shield);
        strongbox.put(ResourceType.SERVANT, servant);
        strongbox.put(ResourceType.STONE, stone);
    }
}
