package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.Game;

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
        Game.getGameInstance()
                .getCurrentPlayer()
                .getPlayerBoard()
                .getFaithTrack()
                .increaseFaithMarker(1);
    }
}
