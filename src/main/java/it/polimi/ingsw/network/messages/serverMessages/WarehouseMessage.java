package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.warehouse.Shelf;
import it.polimi.ingsw.model.warehouse.Warehouse;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

public class WarehouseMessage extends Message {
    private static final long serialVersionUID = 5571450220750121611L;

    private final ArrayList<ResourceType> warehouse;
    private final ArrayList<ResourceType> extra_shelf;

    public WarehouseMessage(Warehouse warehouse) {
        super.setMessageType(PossibleMessages.WAREHOUSE);
        super.setSenderUsername("SERVER_MESSAGE");

        this.warehouse = new ArrayList<>();
        this.extra_shelf = new ArrayList<>();

        for (Shelf iterator : warehouse.getShelves()) {
            for (int i = 0; i < iterator.getCapacity()- iterator.getQuantity(); i++) {
                this.warehouse.add(ResourceType.UNDEFINED);
            }
            for (int i = 0; i < iterator.getQuantity(); i++) {
                this.warehouse.add(iterator.getType());
            }
        }
        if(warehouse.getShelves().size()>3){
            for (int i = 3; i < warehouse.getShelves().size(); i++) {
                extra_shelf.add(warehouse.getShelves().get(i).getType());
            }
        }
    }

    public ArrayList<ResourceType> getWarehouse() {
        return warehouse;
    }

    public ArrayList<ResourceType> getExtra_shelf() {
        return extra_shelf;
    }

    @Override
    public String toString() {
        return "WarehouseMessage{" +
                "warehouse=" + warehouse +
                ", extra shelves =" + extra_shelf +
                '}';
    }
}
