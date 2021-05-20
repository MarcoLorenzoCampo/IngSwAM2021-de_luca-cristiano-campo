package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.Tile;
import it.polimi.ingsw.network.views.cli.ColorCLI;
import it.polimi.ingsw.network.views.cli.constants.GraphicalResourceConstants;

import java.util.HashMap;
import java.util.Map;

public class GraphicalFaithTrackTile {
    private static final int MAX_VERT_TILES = 15; //rows.
    private static final int MAX_HORIZ_TILES = 15; //cols.

    private Tile tile;
    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    private int checkpoint;
    private int vaticanSpace;
    private int index;
    private int faithMarker;
    private ColorCLI colorCard;
    private Integer cardVaticanSpace;

    private Map<Integer, ColorCLI> cardVaticanSpaceColor = new HashMap<>();
    private Map<ResourceType, String> faithMarkerType = new HashMap<>();

    public GraphicalFaithTrackTile(Tile tile, int faithMarker){
        this.tile = tile;
        this.faithMarker = faithMarker;
        borderBuilding();
        loadTile();
        initCardVaticanSpaceColor();
    }

    private void borderBuilding(){
        cells[0][0] = "╭";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[0][c] = "-";
        }

        cells[0][MAX_HORIZ_TILES - 1] = "╮";

        for (int r = 1; r < MAX_VERT_TILES - 1; r++) {
            cells[r][0] = "|";
            for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
                cells[r][c] = " ";
            }
            cells[r][MAX_HORIZ_TILES - 1] = "|";
        }

        cells[MAX_VERT_TILES - 1][0] = "╰";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[MAX_VERT_TILES - 1][c] = "-";
        }

        cells[MAX_VERT_TILES - 1][MAX_HORIZ_TILES - 1] = "╯";
    }

    private void loadTile(){

        this.index = tile.getIndex();
        insertingIndexCard(this.index);

        this.checkpoint = tile.getCheckpoint();
        insertingCheckpoint(this.checkpoint);

        this.vaticanSpace = tile.getVaticanSpace();
        this.colorCard = this.cardVaticanSpaceColor.get(this.vaticanSpace);

        if(this.faithMarker == this.index){
            insertingGraphicalFaithMarker();
        }

        if((this.index == 8) || (this.index == 16) || (this.index == 24)){
            insertingGraphicalPope();
        }
    }

    private void insertingIndexCard(Integer index){
        cells[1][(MAX_HORIZ_TILES / 2) - 1] = " " + index;
        cells[2][1] = "  ";
        cells[3][1] = "  ";
        cells[4][1] = "  ";
    }

    private void insertingGraphicalFaithMarker(){
        cells[5][5] = "\uD83D\uDD47" + " ";
        cells[6][1] = "  ";
        cells[7][1] = "  ";
        cells[8][1] = "  ";
    }

    private void insertingGraphicalPope(){
        cells[9][5] = "\u26EA";
        cells[10][1] = "  ";
    }

    private void insertingCheckpoint(Integer checkpoint){
        cells[11][(MAX_HORIZ_TILES / 2) - 1] = "" + checkpoint;
        cells[12][1] = "  ";
    }


    private void initCardVaticanSpaceColor(){
        cardVaticanSpaceColor.put(0, ColorCLI.ANSI_BRIGHT_WHITE);
        cardVaticanSpaceColor.put(1, ColorCLI.ANSI_BRIGHT_YELLOW);
        cardVaticanSpaceColor.put(2, ColorCLI.ANSI_BRIGHT_RED);
        cardVaticanSpaceColor.put(3, ColorCLI.ANSI_RED);
    }


    public void draw() {
        System.out.print(this.colorCard.escape());
        for (int r = 0; r < MAX_VERT_TILES; r++) {
            System.out.println();
            for (int c = 0; c < MAX_HORIZ_TILES; c++) {
                System.out.print(cells[r][c]);
            }
        }
    }
}

