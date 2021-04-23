package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.MultiPlayerGame;

public class MaterialResource extends Resource {

    /* constructor sets the type of the MaterialResource create */
    public MaterialResource(ResourceType resourceType) {
        this.setResourceType(resourceType);
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
                .getInventoryManager()
                .deposit(this);
    }


    /* overloaded setter */
    public MaterialResource(String resourceType) {

        if(resourceType.equals(ResourceType.COIN.toString())) {
            super.setResourceType(ResourceType.COIN);
        }
        if(resourceType.equals(ResourceType.SERVANT.toString())) {
            super.setResourceType(ResourceType.SERVANT);
        }
        if(resourceType.equals(ResourceType.SHIELD.toString())) {
            super.setResourceType(ResourceType.SHIELD);
        }
        if(resourceType.equals(ResourceType.STONE.toString())) {
            super.setResourceType(ResourceType.STONE);
        }
        if(resourceType.equals(ResourceType.UNDEFINED.toString())) {
            super.setResourceType(ResourceType.UNDEFINED);
        }
    }

}
