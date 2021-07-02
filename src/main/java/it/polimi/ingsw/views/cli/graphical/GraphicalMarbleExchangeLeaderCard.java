package it.polimi.ingsw.views.cli.graphical;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.views.cli.ColorCLI;

import java.util.HashMap;
import java.util.Map;

public class GraphicalMarbleExchangeLeaderCard {
    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12; //cols.

    private LeaderCard leaderCard;



    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    //final points
    private int victoryPoints;
    //requisiti di due carte di due colori diversi
    private DevelopmentTag[] requirementsDevCards;
    //descrizione potere
    private ResourceType resourceExchanged;
    private ColorCLI colorRequirements1;
    private ColorCLI colorRequirements2;
    private ColorCLI colorResourceExchanged;
    private Map<Color, ColorCLI> colorRequirementsDevCards = new HashMap<>();
    private Map<ResourceType, ColorCLI> colorResource = new HashMap<>();

    public GraphicalMarbleExchangeLeaderCard(LeaderCard leaderCard){
        initColorRequirementsDevCards();
        initColorResourceExchanged();
        this.leaderCard = leaderCard;
        this.victoryPoints = leaderCard.getVictoryPoints();
        this.colorRequirements1 = colorRequirementsDevCards.get(this.leaderCard.getRequirementsDevCards()[0].getColor());
        this.colorRequirements2 = colorRequirementsDevCards.get(this.leaderCard.getRequirementsDevCards()[1].getColor());
        this.colorResourceExchanged = colorResource.get(this.leaderCard.getResource());
        borderBuilding();
        loadMarbleExchangeCard(this.colorRequirements1, this.colorRequirements2 ,this.colorResourceExchanged);

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



    private void loadMarbleExchangeCard( ColorCLI color1, ColorCLI color2, ColorCLI color3){

        cells[1][1] = color1.escape() + "2";

        cells[1][2] = "[";
        cells[1][3] = "]";
        cells[1][4] = color2.escape() + "1";

        cells[1][5] =  "[";
        cells[1][6] = "]" + ColorCLI.getRESET();

        if(this.leaderCard.isActive()){
            cells[1][9] = ColorCLI.ANSI_GREEN.escape() + "A" + ColorCLI.getRESET();
        }
        else{
            cells[1][9] = ColorCLI.ANSI_RED.escape() + "N" + ColorCLI.getRESET();
        }

        cells[3][6] = "" + this.victoryPoints;


        cells[6][4] = "O";
        cells[6][6] = "=";
        cells[6][8] = color3.escape() + "@" + ColorCLI.getRESET();

    }



    private void initColorRequirementsDevCards(){
        colorRequirementsDevCards.put(Color.GREEN, ColorCLI.ANSI_GREEN);
        colorRequirementsDevCards.put(Color.BLUE, ColorCLI.ANSI_BLUE);
        colorRequirementsDevCards.put(Color.YELLOW, ColorCLI.ANSI_YELLOW);
        colorRequirementsDevCards.put(Color.PURPLE, ColorCLI.ANSI_PURPLE);
    }

    private void initColorResourceExchanged(){
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
