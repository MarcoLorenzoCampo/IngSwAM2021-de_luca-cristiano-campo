package it.polimi.ingsw.model.warehouse;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.ResourceTag;

import static it.polimi.ingsw.enumerations.ResourceType.UNDEFINED;

/**
 * Define a container that can only hold up to a maxCapacity and can only hold Resources of a specific type.
 * When it's empty the type is defined by the first resource placed
 */

public class Shelf  {
    private ResourceTag element;
    private int capacity;

    public Shelf(ResourceType type, int maxCapacity) {
        this.element = new ResourceTag(type, 0);
        this.capacity = maxCapacity;
    }

    public Shelf (Shelf shelf){
        this.element = new ResourceTag(shelf.getType(), shelf.getQuantity());
        this.capacity = shelf.getCapacity();
    }

    public ResourceTag getElement() {
        return element;
    }

    public void setElement(ResourceTag element) {
        this.element.setType(element.getType());
        this.element.setQuantity(element.getQuantity());
    }

    public boolean isValid() {
        if(this.getType().equals(UNDEFINED)){
            return this.getQuantity() == 0;
        }
        else{
            return this.getQuantity() <= capacity;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public int getQuantity(){
        return this.getElement().getQuantity();
    }

    public void increaseQuantity(){
        this.getElement().setQuantity(this.getElement().getQuantity()+1);
    }

    public void decreaseQuantity(){
        if(this.getElement().getQuantity() > 0){
            this.getElement().setQuantity(this.getElement().getQuantity()-1);
        }
    }

    public void clear(){
        if(this.getQuantity() == 0){
            this.getElement().setType(UNDEFINED);
        }
    }
    public ResourceType getType(){
        return this.getElement().getType();
    }


}

