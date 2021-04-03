package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.ResourceType;

public class FaithResource extends Resource {

    public FaithResource() {
        this.setResourceType(ResourceType.FAITH);
    }

    @Override
    public void setResourceType(ResourceType resourceType) {
        super.setResourceType(resourceType);
    }

    @Override
    public ResourceType getResourceType() {
        return super.getResourceType();
    }

    @Override
    public void deposit() {
        //playerBoard.faithTrack.move();
    }
}
