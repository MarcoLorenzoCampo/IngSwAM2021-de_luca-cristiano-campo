package it.polimi.ingsw.model.utilities;

import it.polimi.ingsw.enumerations.ResourceType;

import java.io.Serializable;

public class ResourceTag extends Tag implements Serializable {
    private static final long serialVersionUID = -6385279486823380885L;
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
        return type +
                super.toString();
    }
}
