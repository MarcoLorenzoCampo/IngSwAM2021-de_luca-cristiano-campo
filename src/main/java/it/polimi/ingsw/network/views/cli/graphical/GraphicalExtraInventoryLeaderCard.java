package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.views.cli.ColorCLI;

import java.util.HashMap;
import java.util.Map;

public class GraphicalExtraInventoryLeaderCard {
    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12; //cols.

    private LeaderCard leaderCard;



    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    //final points
    private int victoryPoints;
    //requisiti di risorse
    private ResourceType resourceRequirements;
    //descrizione potere
    private ResourceType resourceExtra;
    private ColorCLI colorResourceRequirements;
    private ColorCLI colorResourceExtra;
    private Map<ResourceType, ColorCLI> colorResource = new HashMap<>();

    public GraphicalExtraInventoryLeaderCard(LeaderCard leaderCard){
        initColorResource();
        this.leaderCard = leaderCard;
        this.victoryPoints = leaderCard.getVictoryPoints();
        this.colorResourceRequirements = colorResource.get(this.leaderCard.getRequirementsResource()[0].getType());
        this.colorResourceExtra = colorResource.get(this.leaderCard.getResource());
        borderBuilding();
        loadDiscountCard(this.colorResourceRequirements, this.colorResourceExtra);

    }

    private void borderBuilding() {

        cells[0][0] = "╔";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[0][c] = "═";
        }

        cells[0][MAX_HORIZ_TILES - 1] = "╗";

        for (int r = 1; r < MAX_VERT_TILES - 1; r++) {
            cells[r][0] = "║";
            for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
                cells[r][c] = " ";
            }
            cells[r][MAX_HORIZ_TILES-1] = "║";
        }

        cells[MAX_VERT_TILES - 1][0] =  "╚";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[MAX_VERT_TILES - 1][c] =  "═";
        }

        cells[MAX_VERT_TILES - 1][MAX_HORIZ_TILES - 1] = "╝";

    }


    private void loadDiscountCard(ColorCLI color1, ColorCLI color2){

        cells[1][1] = "" + leaderCard.getRequirementsResource()[0].getQuantity();

        cells[1][2] = color1.escape() + "@" + ColorCLI.getRESET();

        if(this.leaderCard.isActive()){
            cells[1][9] = ColorCLI.ANSI_GREEN.escape() + "A" + ColorCLI.getRESET();
        }
        else{
            cells[1][9] = ColorCLI.ANSI_RED.escape() + "N" + ColorCLI.getRESET();
        }

        cells[3][6] = "" + this.victoryPoints;


        cells[6][4] = color2.escape() + "@";
        cells[6][8] =  "@" + ColorCLI.getRESET();


    }


    private void initColorResource(){
        colorResource.put(ResourceType.STONE, ColorCLI.ANSI_WHITE);
        colorResource.put(ResourceType.COIN, ColorCLI.ANSI_YELLOW);
        colorResource.put(ResourceType.SHIELD, ColorCLI.ANSI_BLUE);
        colorResource.put(ResourceType.SERVANT, ColorCLI.ANSI_PURPLE);
        colorResource.put(ResourceType.UNDEFINED, ColorCLI.ANSI_BRIGHT_WHITE);
    }

    public int getMaxVertTiles() {
        return MAX_VERT_TILES;
    }

    public int getMaxHorizTiles() {
        return MAX_HORIZ_TILES;
    }

    public String[][] getCells() {
        return cells;
    }
}
