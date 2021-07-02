package it.polimi.ingsw.views.cli.graphical;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.views.cli.ColorCLI;
import it.polimi.ingsw.views.cli.LightweightModel;
import it.polimi.ingsw.views.cli.constants.GraphicalResourceConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphicalWarehouse {

    private final LightweightModel lightweightModel;
    private final ArrayList<ResourceType> shelves;
    private final ArrayList<ResourceType> extras;

    private final Map<ResourceType, ColorCLI> colorResourceExtra = new HashMap<>();
    private final Map<ResourceType, String> graphicalResource = new HashMap<>();

    public GraphicalWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras) {
        initColorResourceExtras();
        initGraphicalResource();
        this.shelves = shelves;
        this.extras = extras;
        this.lightweightModel = new LightweightModel();
        updatingWarehouse(this.shelves, this.extras);
    }

    public void updatingWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras){

        System.out.println("Warehouse: ");
        System.out.println("SHELF 1 = " + (colorResourceExtra.get(shelves.get(0))).escape() + graphicalResource.get(shelves.get(0)) + ColorCLI.getRESET());
        System.out.println("SHELF 2 = " + (colorResourceExtra.get(shelves.get(1))).escape() + graphicalResource.get(shelves.get(1)) + " " + (colorResourceExtra.get(shelves.get(2))).escape() + graphicalResource.get(shelves.get(2)) + ColorCLI.getRESET());
        System.out.println("SHELF 3 = " + (colorResourceExtra.get(shelves.get(3))).escape() + graphicalResource.get(shelves.get(3)) + " " + (colorResourceExtra.get(shelves.get(4))).escape() + graphicalResource.get(shelves.get(4)) + " " + (colorResourceExtra.get(shelves.get(5))).escape() + graphicalResource.get(shelves.get(5)) + ColorCLI.getRESET());

        System.out.println();

        System.out.println("EXTRA SHELVES: " + extras);
        if(shelves.size()>6){
            for (int i = 6; i < shelves.size(); i++) {
                System.out.print((colorResourceExtra.get(shelves.get(i))).escape() + graphicalResource.get(shelves.get(i)) + ColorCLI.getRESET() + " ");
            }
        }
        System.out.println();
    }

    private void initColorResourceExtras(){
        colorResourceExtra.put(ResourceType.STONE, ColorCLI.ANSI_WHITE);
        colorResourceExtra.put(ResourceType.COIN, ColorCLI.ANSI_YELLOW);
        colorResourceExtra.put(ResourceType.SHIELD, ColorCLI.ANSI_BLUE);
        colorResourceExtra.put(ResourceType.SERVANT, ColorCLI.ANSI_PURPLE);
        colorResourceExtra.put(ResourceType.UNDEFINED, ColorCLI.ANSI_BRIGHT_WHITE);
    }

    private void initGraphicalResource(){
        graphicalResource.put(ResourceType.SERVANT, GraphicalResourceConstants.servant);
        graphicalResource.put(ResourceType.COIN, GraphicalResourceConstants.coin);
        graphicalResource.put(ResourceType.SHIELD, GraphicalResourceConstants.shield);
        graphicalResource.put(ResourceType.STONE, GraphicalResourceConstants.stone);
        graphicalResource.put(ResourceType.UNDEFINED, GraphicalResourceConstants.undefined);
    }
}
