package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.ResourceType;

public class ResourceTag extends Tag {
    private ResourceType type;

    public ResourceTag (ResourceType type, int quantity ){
        this.setQuantity(quantity);
        this.type=type;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ResourceTag{" +
                "type=" + type +
                super.toString() +
                '}';
    }
}
