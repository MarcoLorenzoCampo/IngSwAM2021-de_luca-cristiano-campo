package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.ResourceTag;

/**
 * Exception used to notify that either the strongbox or the warehouse
 * doesn't have enough resources to suffice the remove action, and so the resources
 * need to be removed from the other source
 */
public class CannotRemoveResourceException extends Exception {
    ResourceTag remainingResources;

    public CannotRemoveResourceException(ResourceType type, int quantity){
        remainingResources = new ResourceTag(type, quantity);
    }

    public ResourceType getType(){
        return remainingResources.getType();
    }

    public int getQuantity(){
        return remainingResources.getQuantity();
    }
}


