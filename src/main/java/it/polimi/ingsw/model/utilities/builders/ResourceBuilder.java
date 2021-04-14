package it.polimi.ingsw.model.utilities.builders;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.FaithResource;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.Resource;

import java.util.LinkedList;

/**
 * Utility class needed to build resources based on a specific ResourceType.
 */
public final class ResourceBuilder {
    public static LinkedList<Resource> build(LinkedList<ResourceType> pickedFromMarket) {

        LinkedList<Resource> obtainedResources = new LinkedList<>();

        for(ResourceType resourceType : pickedFromMarket) {
            if(resourceType.equals(ResourceType.FAITH)) {
                obtainedResources.add(new FaithResource());
            } else {
                obtainedResources.add(new MaterialResource(resourceType));
            }
        }
        return obtainedResources;
    }
}
