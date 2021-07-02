package it.polimi.ingsw.views.cli.graphical;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.views.cli.ColorCLI;

import java.util.HashMap;
import java.util.Map;

public class GraphicalExtraProductionLeaderCard {
    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12; //cols.

    private LeaderCard leaderCard;


    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    //final points
    private int victoryPoints;
    //requisiti di due carte di due colori diversi
    private DevelopmentTag requirementsDevCards;
    //descrizione potere
    private ResourceType resourceGiven;
    private ColorCLI colorRequirements;
    private ColorCLI colorResource;
    private Map<Color, ColorCLI> colorRequirementsDevCards = new HashMap<>();
    private Map<ResourceType, ColorCLI> colorResourceGiven = new HashMap<>();

    public GraphicalExtraProductionLeaderCard(LeaderCard leaderCard) {
        initColorRequirementsDevCards();
        initColorResourceGiven();
        this.leaderCard = leaderCard;
        this.victoryPoints = leaderCard.getVictoryPoints();
        this.colorRequirements = colorRequirementsDevCards.get(this.leaderCard.getRequirementsDevCards()[0].getColor());
        this.colorResource = colorResourceGiven.get(this.leaderCard.getResource());
        borderBuilding();
        loadMarbleExchangeCard(this.colorRequirements, this.colorResource);


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
            cells[r][MAX_HORIZ_TILES - 1] = "║";
        }

        cells[MAX_VERT_TILES - 1][0] = "╚";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[MAX_VERT_TILES - 1][c] = "═";
        }

        cells[MAX_VERT_TILES - 1][MAX_HORIZ_TILES - 1] = "╝";

    }


    private void loadMarbleExchangeCard(ColorCLI color1, ColorCLI color2) {


        cells[1][1] = "1";
        cells[1][2] = color1.escape() + "[";
        cells[1][3] = ":";
        cells[1][4] = "]" + ColorCLI.getRESET();

        if(this.leaderCard.isActive()){
            cells[1][9] = ColorCLI.ANSI_GREEN.escape() + "A" + ColorCLI.getRESET();
        }
        else{
            cells[1][9] = ColorCLI.ANSI_RED.escape() + "N" + ColorCLI.getRESET();
        }


        cells[3][6] = "" + this.victoryPoints;


        cells[6][2] = color2.escape() + "@" + ColorCLI.getRESET();
        cells[6][3] = "-";
        cells[6][4] = ">";
        cells[6][6] = "1";
        cells[6][7] = "?";
        cells[6][8] = "1";
        cells[6][9] = ColorCLI.ANSI_RED.escape() + "@" + ColorCLI.getRESET();

    }


    private void initColorRequirementsDevCards() {
        colorRequirementsDevCards.put(Color.GREEN, ColorCLI.ANSI_GREEN);
        colorRequirementsDevCards.put(Color.BLUE, ColorCLI.ANSI_BLUE);
        colorRequirementsDevCards.put(Color.YELLOW, ColorCLI.ANSI_YELLOW);
        colorRequirementsDevCards.put(Color.PURPLE, ColorCLI.ANSI_PURPLE);
    }

    private void initColorResourceGiven() {
        colorResourceGiven.put(ResourceType.STONE, ColorCLI.ANSI_WHITE);
        colorResourceGiven.put(ResourceType.COIN, ColorCLI.ANSI_YELLOW);
        colorResourceGiven.put(ResourceType.SHIELD, ColorCLI.ANSI_BRIGHT_BLUE);
        colorResourceGiven.put(ResourceType.SERVANT, ColorCLI.ANSI_PURPLE);
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
