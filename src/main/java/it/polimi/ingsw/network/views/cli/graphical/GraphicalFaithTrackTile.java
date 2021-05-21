package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.Tile;
import it.polimi.ingsw.network.views.cli.ColorCLI;
import it.polimi.ingsw.network.views.cli.constants.GraphicalResourceConstants;

import java.util.HashMap;
import java.util.Map;

public class GraphicalFaithTrackTile {
    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12; //cols.

    private final Tile tile;



    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    private int checkpoint;
    private int vaticanSpace;
    private int index;
    private final int faithMarker;
    private ColorCLI colorCard;
    private Integer cardVaticanSpace;

    private final Map<Integer, ColorCLI> cardVaticanSpaceColor;
    private final Map<ResourceType, String> faithMarkerType = new HashMap<>();

    public GraphicalFaithTrackTile(Tile tile, int faithMarker){
        cardVaticanSpaceColor = new HashMap<>();
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
        if(this.index < 10){
        insertingIndexCard(this.index); }
        else if(this.index == 10){
            insertingIndexCard10();
        }
        else if(this.index == 11){
            insertingIndexCard11();
        }
        else if(this.index == 12){
            insertingIndexCard12();
        }
        else if(this.index == 13){
            insertingIndexCard13();
        }
        else if(this.index == 14){
            insertingIndexCard14();
        }
        else if(this.index == 15){
            insertingIndexCard15();
        }
        else if(this.index == 16){
            insertingIndexCard16();
        }
        else if(this.index == 17){
            insertingIndexCard17();
        }
        else if(this.index == 18){
            insertingIndexCard18();
        }
        else if(this.index == 19){
            insertingIndexCard19();
        }
        else if(this.index == 20){
            insertingIndexCard20();
        }
        else if(this.index == 21){
            insertingIndexCard21();
        }
        else if(this.index == 22){
            insertingIndexCard22();
        }
        else if(this.index == 23){
            insertingIndexCard23();
        }
        else if(this.index == 24){
            insertingIndexCard24();
        }
        else{
            insertingIndexCard25();
        }

        this.checkpoint = tile.getCheckpoint();
        if(this.checkpoint < 10){
        insertingCheckpoint(this.checkpoint);
        }
        else if (this.checkpoint == 12){
            insertingCheckpoint12();
        }
        else if (this.checkpoint == 16){
            insertingCheckpoint16();
        }
        else {
            insertingCheckpoint20();
        }

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
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="" + index;

    }

    private void insertingIndexCard10(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="0";
    }

    private void insertingIndexCard11(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="1";
    }

    private void insertingIndexCard12(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="2";
    }

    private void insertingIndexCard13(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="3";
    }

    private void insertingIndexCard14(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="4";
    }

    private void insertingIndexCard15(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="5";
    }

    private void insertingIndexCard16(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="6";
    }

    private void insertingIndexCard17(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="7";
    }

    private void insertingIndexCard18(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="8";
    }
    private void insertingIndexCard19(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="1";
        cells[1][8]="9";
    }

    private void insertingIndexCard20(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="2";
        cells[1][8]="0";
    }

    private void insertingIndexCard21(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="2";
        cells[1][8]="1";
    }

    private void insertingIndexCard22(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="2";
        cells[1][8]="2";
    }

    private void insertingIndexCard23(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="2";
        cells[1][8]="3";
    }

    private void insertingIndexCard24(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="2";
        cells[1][8]="4";
    }

    private void insertingIndexCard25(){
        cells[1][1]="I";
        cells[1][2]="N";
        cells[1][3]="D";
        cells[1][4]="E";
        cells[1][5]="X";
        cells[1][7]="2";
        cells[1][8]="5";
    }


    private void insertingGraphicalFaithMarker(){

        cells[3][1] = "M";
        cells[3][2] = "A";
        cells[3][3] = "R";
        cells[3][4] = "K";
        cells[3][5] = "E";
        cells[3][6] = "R";

    }

    private void insertingGraphicalPope(){
        cells[4][1] = "P";
        cells[4][2] = "O";
        cells[4][3] = "P";
        cells[4][4] = "E";
        cells[4][5] = "T";
        cells[4][6] = "I";
        cells[4][7] = "L";
        cells[4][8] = "E";
    }

    private void insertingCheckpoint(Integer checkpoint){
        cells[5][1] = "C";
        cells[5][2] = "H";
        cells[5][3] = "E";
        cells[5][4] = "C";
        cells[5][5] = "K";
        cells[6][1] = "P";
        cells[6][2] = "O";
        cells[6][3] = "I";
        cells[6][4] = "N";
        cells[6][5] = "T";
        cells[6][7] = "" + checkpoint;

    }

    private void insertingCheckpoint12(){
        cells[5][1] = "C";
        cells[5][2] = "H";
        cells[5][3] = "E";
        cells[5][4] = "C";
        cells[5][5] = "K";
        cells[6][1] = "P";
        cells[6][2] = "O";
        cells[6][3] = "I";
        cells[6][4] = "N";
        cells[6][5] = "T";
        cells[6][7] = "1";
        cells[6][8] = "2";
    }

    private void insertingCheckpoint16(){
        cells[5][1] = "C";
        cells[5][2] = "H";
        cells[5][3] = "E";
        cells[5][4] = "C";
        cells[5][5] = "K";
        cells[6][1] = "P";
        cells[6][2] = "O";
        cells[6][3] = "I";
        cells[6][4] = "N";
        cells[6][5] = "T";
        cells[6][7] = "1";
        cells[6][8] = "6";
    }

    private void insertingCheckpoint20(){
        cells[5][1] = "C";
        cells[5][2] = "H";
        cells[5][3] = "E";
        cells[5][4] = "C";
        cells[5][5] = "K";
        cells[6][1] = "P";
        cells[6][2] = "O";
        cells[6][3] = "I";
        cells[6][4] = "N";
        cells[6][5] = "T";
        cells[6][7] = "2";
        cells[6][8] = "0";
    }


    private void initCardVaticanSpaceColor(){
        cardVaticanSpaceColor.put(0, ColorCLI.ANSI_BRIGHT_WHITE);
        cardVaticanSpaceColor.put(1, ColorCLI.ANSI_BRIGHT_YELLOW);
        cardVaticanSpaceColor.put(2, ColorCLI.ANSI_BRIGHT_RED);
        cardVaticanSpaceColor.put(3, ColorCLI.ANSI_RED);
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

