package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utilities.Resource;

import java.util.List;

public class InventoryManager {

    List<Resource> materialResourceBuffer;

    public void setMaterialResourceBuffer(List<Resource> obtainedResources) {
        materialResourceBuffer = obtainedResources;
    }


}
