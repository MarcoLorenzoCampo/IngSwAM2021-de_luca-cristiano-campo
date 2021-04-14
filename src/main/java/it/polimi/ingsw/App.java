package it.polimi.ingsw;


import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.resourceManager.ResourceManager;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.warehouse.Shelf;
import it.polimi.ingsw.model.warehouse.Warehouse;

import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args){
        ResourceManager resourceManager = new ResourceManager();
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        resourceManager.deposit(new MaterialResource(ResourceType.SERVANT));
        resourceManager.deposit(new MaterialResource(ResourceType.SERVANT));
        resourceManager.deposit(new MaterialResource(ResourceType.SERVANT));
        resourceManager.deposit(new MaterialResource(ResourceType.SERVANT));
        resourceManager.deposit(new MaterialResource(ResourceType.SERVANT));
        resourceManager.deposit(new MaterialResource(ResourceType.SERVANT));
        resourceManager.deposit(new MaterialResource(ResourceType.STONE));
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        boolean iterate = true;
        while (iterate){
            for (MaterialResource iterator:resourceManager.getBuffer()) {
                System.out.print(i + " = "+iterator.getResourceType()+ " ");
                i++;
            }
            System.out.println();
            resourceManager.addResourceToWarehouse(scanner.nextInt());
            i = 0;

            if(resourceManager.getBuffer().isEmpty()){
                System.out.println("all resources have been put in the warehouse");
                System.out.println();
                iterate = false;
            }
        }
        resourceManager.deposit(new MaterialResource(ResourceType.SERVANT));
        resourceManager.deposit(new MaterialResource(ResourceType.STONE));
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        resourceManager.deposit(new MaterialResource(ResourceType.COIN));
        for (MaterialResource iterator:resourceManager.getBuffer()) {
            resourceManager.addResourceToStrongbox(iterator.getResourceType());

        }

        int quantity;
        Warehouse prova = resourceManager.getWarehouse();
        for (Shelf iterator: prova.getShelves()) {
            quantity = iterator.getQuantity();
            for (int j = 0; j < iterator.getCapacity(); j++) {
                if(quantity > 0) System.out.print(iterator.getType() + " ");
                else System.out.print("@");
            }
            System.out.println();
        }

        for (Map.Entry<ResourceType, Integer> iterator: resourceManager.getInventory().entrySet()) {
            System.out.print(iterator + " ");
        }
    }
}
