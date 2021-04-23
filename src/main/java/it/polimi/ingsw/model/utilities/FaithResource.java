package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.MultiPlayerGame;

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
        MultiPlayerGame.getGameInstance()
                .getCurrentPlayer()
                .getPlayerBoard()
                .getFaithTrack()
                .increaseFaithMarker();
    }
}
