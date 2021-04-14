package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.ResourceTag;

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

