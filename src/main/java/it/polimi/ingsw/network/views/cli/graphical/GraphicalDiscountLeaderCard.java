package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.views.cli.ColorCLI;

import java.util.HashMap;
import java.util.Map;

public class GraphicalDiscountLeaderCard {
    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12; //cols.

    private final LeaderCard leaderCard;



    private final String[][] cells = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    //final points
    private final int victoryPoints;
    private final Map<Color, ColorCLI> colorRequirementsDevCards = new HashMap<>();
    private final Map<ResourceType, ColorCLI> colorResource = new HashMap<>();

    public GraphicalDiscountLeaderCard(LeaderCard leaderCard){
        initColorRequirementsDevCards();
        initColorResourceDiscounted();
        this.leaderCard = leaderCard;
        this.victoryPoints = leaderCard.getVictoryPoints();
        ColorCLI colorRequirements1 = colorRequirementsDevCards.get(this.leaderCard.getRequirementsDevCards()[0].getColor());
        ColorCLI getColorRequirements2 = colorRequirementsDevCards.get(this.leaderCard.getRequirementsDevCards()[1].getColor());
        ColorCLI colorResourceDiscounted = colorResource.get(this.leaderCard.getResource());
        borderBuilding();
        loadDiscountCard(colorRequirements1, getColorRequirements2, colorResourceDiscounted);
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



    private void loadDiscountCard(ColorCLI color1, ColorCLI color2, ColorCLI color3){

        cells[1][1] = "1";

        cells[1][2] = color1.escape() + "[";
        cells[1][3] =  "]" + ColorCLI.getRESET();
        cells[1][4] =  "1";
        cells[1][5] =  color2.escape() + "[";
        cells[1][6] =  "]" + ColorCLI.getRESET();

        if(this.leaderCard.isActive()){
            cells[1][9] = ColorCLI.ANSI_GREEN.escape() + "A" + ColorCLI.getRESET();
        }
        else{
            cells[1][9] = ColorCLI.ANSI_RED.escape() + "N" + ColorCLI.getRESET();
        }

        cells[3][6] = "" + this.victoryPoints;

        cells[6][8] = "-";
        cells[6][9] = "1";
        cells[6][10] = color3.escape() + "@" + ColorCLI.getRESET();

    }



    private void initColorRequirementsDevCards(){
        colorRequirementsDevCards.put(Color.GREEN, ColorCLI.ANSI_GREEN);
        colorRequirementsDevCards.put(Color.BLUE, ColorCLI.ANSI_BLUE);
        colorRequirementsDevCards.put(Color.YELLOW, ColorCLI.ANSI_YELLOW);
        colorRequirementsDevCards.put(Color.PURPLE, ColorCLI.ANSI_PURPLE);
    }

    private void initColorResourceDiscounted(){
        colorResource.put(ResourceType.STONE, ColorCLI.ANSI_WHITE);
        colorResource.put(ResourceType.COIN, ColorCLI.ANSI_YELLOW);
        colorResource.put(ResourceType.SHIELD, ColorCLI.ANSI_BRIGHT_BLUE);
        colorResource.put(ResourceType.SERVANT, ColorCLI.ANSI_PURPLE);
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

