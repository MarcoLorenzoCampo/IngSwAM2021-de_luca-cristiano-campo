package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.player.RealPlayerBoard;

public abstract class Resource {

    private ResourceType resourceType;

    /* deposit() is used to deposit the resource. Overridden by MaterialResource and FaithResource */
    public void deposit(RealPlayerBoard playerBoard) { }

    /* default setter */
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    /* overloaded setter */
    public void setResourceType(String resourceType) {

        if(resourceType.equals(ResourceType.COIN.toString())) {
            this.resourceType = ResourceType.COIN;
        }
        if(resourceType.equals(ResourceType.SERVANT.toString())) {
            this.resourceType = ResourceType.SERVANT;
        }
        if(resourceType.equals(ResourceType.SHIELD.toString())) {
            this.resourceType = ResourceType.SHIELD;
        }
        if(resourceType.equals(ResourceType.STONE.toString())) {
            this.resourceType = ResourceType.STONE;
        }
        if(resourceType.equals(ResourceType.UNDEFINED.toString())) {
            this.resourceType = ResourceType.UNDEFINED;
        }
    }

    /* default getter */
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;
        Resource resource = (Resource) o;
        return getResourceType() == resource.getResourceType();
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceType=" + resourceType +
                '}';
    }
}