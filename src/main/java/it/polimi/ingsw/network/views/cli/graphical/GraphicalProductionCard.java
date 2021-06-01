package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.views.cli.ColorCLI;

import it.polimi.ingsw.network.views.cli.constants.GraphicalResourceConstants;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphicalProductionCard {

    private static final int MAX_VERT_TILES = 12; //rows.
    private static final int MAX_HORIZ_TILES = 12; //cols.

    private ProductionCard productionCard;

    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    private int index;
    private int level;
    private ColorCLI color;
    private int victoryPoints;
    private ResourceType requirementsType1;
    private ResourceType requirementsType2;
    private Integer requirementsQuantity1;
    private Integer requirementsQuantity2;
    private ResourceType requirementsInputType1;
    private ResourceType requirementsInputType2;
    private ResourceType requirementsOutputType;
    private ResourceType requirementsOutputType1;
    private ResourceType requirementsOutputType2;
    private ResourceType requirementsOutputType3;

    private Integer requirementsInputQuantity1;
    private Integer requirementsInputQuantity2;
    private Integer requirementsOutputQuantity;
    private Integer requirementsOutputQuantity1;
    private Integer requirementsOutputQuantity2;
    private Integer requirementsOutputQuantity3;

    private Map<Level, Integer> cardLevel = new HashMap<>();
    private Map<Color, ColorCLI> cardColor = new HashMap<>();
    private Map<ResourceType, ColorCLI> colorRequirementsType = new HashMap<>();

    public GraphicalProductionCard(ProductionCard productionCard, Integer i) {
        initCardRequirementsType();
        initColorLevel();
        initCardLevel();
        this.productionCard = productionCard;
        loadAvailableCard();
        loadIndexCard(i);
    }

    public GraphicalProductionCard(ProductionCard productionCard){
        initCardRequirementsType();
        initColorLevel();
        initCardLevel();
        this.productionCard = productionCard;
        loadAvailableCard();
    }

    private void borderBuilding(ColorCLI color) {

        cells[0][0] = color.escape() + "╭" + ColorCLI.getRESET();
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[0][c] = color.escape() + "-" + ColorCLI.getRESET();
        }

        cells[0][MAX_HORIZ_TILES - 1] = color.escape() + "╮" + ColorCLI.getRESET();

        for (int r = 1; r < MAX_VERT_TILES - 1; r++) {
            cells[r][0] = color.escape() + "|" + ColorCLI.getRESET();
            for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
                cells[r][c] = color.escape() + " " + ColorCLI.getRESET();
            }
            cells[r][MAX_HORIZ_TILES - 1] = color.escape() + "|" + ColorCLI.getRESET();
        }

        cells[MAX_VERT_TILES - 1][0] = color.escape() + "╰" + ColorCLI.getRESET();
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[MAX_VERT_TILES - 1][c] = color.escape() + "-" + ColorCLI.getRESET();
        }

        cells[MAX_VERT_TILES - 1][MAX_HORIZ_TILES - 1] = color.escape() + "╯" + ColorCLI.getRESET();

    }

    private void loadAvailableCard(){

        this.color = this.cardColor.get(this.productionCard.getColor());
        borderBuilding(this.color);

        this.level = this.cardLevel.get(this.productionCard.getLevel());
        insertingLevelCard(this.level);

        this.victoryPoints = this.productionCard.getVictoryPoints();

        if(this.victoryPoints < 10){
            insertingVictoryPointsCard(this.victoryPoints); }
        else if(this.victoryPoints == 10) {
            insertingVictoryPointsCard10();
        }
        else if(this.victoryPoints == 11){
            insertingVictoryPointsCard11();
        }
        else{
            insertingVictoryPointsCard12();
        }

        ArrayList<ResourceTag> resourceTagRequirements = this.productionCard.getRequirements();
        if(resourceTagRequirements.size()==1){
            this.requirementsQuantity1 = resourceTagRequirements.get(0).getQuantity();
            this.requirementsType1 = resourceTagRequirements.get(0).getType();
            ColorCLI colorResource = this.colorRequirementsType.get(this.requirementsType1);
            insertingRequirementsCard(this.requirementsQuantity1, colorResource); }
        else{
            this.requirementsQuantity1 = resourceTagRequirements.get(0).getQuantity();
            this.requirementsType1 = resourceTagRequirements.get(0).getType();
            ColorCLI colorResource1 = this.colorRequirementsType.get(this.requirementsType1);
            this.requirementsQuantity2 = resourceTagRequirements.get(1).getQuantity();
            this.requirementsType2 = resourceTagRequirements.get(1).getType();
            ColorCLI colorResource2 = this.colorRequirementsType.get(this.requirementsType2);
            insertingRequirementsCard(this.requirementsQuantity1, colorResource1, this.requirementsQuantity2, colorResource2);
        }

        ArrayList<ResourceTag> resourceTagInputResource = productionCard.getInputResources();
        if(resourceTagInputResource.size() == 1){
            this.requirementsInputQuantity1 = resourceTagInputResource.get(0).getQuantity();
            this.requirementsInputType1 = resourceTagInputResource.get(0).getType();
            ColorCLI colorResource = this.colorRequirementsType.get(this.requirementsInputType1);
            insertingInputResourcesCard(this.requirementsInputQuantity1, colorResource); }
        else  {
            this.requirementsInputQuantity1 = resourceTagInputResource.get(0).getQuantity();
            this.requirementsInputType1 = resourceTagInputResource.get(0).getType();
            ColorCLI colorResource1 = this.colorRequirementsType.get(this.requirementsInputType1);
            this.requirementsInputQuantity2 = resourceTagInputResource.get(1).getQuantity();
            this.requirementsInputType2 = resourceTagInputResource.get(1).getType();
            ColorCLI colorResource2 = this.colorRequirementsType.get(this.requirementsInputType2);
            insertingInputResourcesCard(this.requirementsInputQuantity1, colorResource1, this.requirementsInputQuantity2, colorResource2);
        }


        ArrayList<ResourceTag> resourceTagOutputResource = productionCard.getOutputResources();
        if(resourceTagOutputResource.size() == 1){
            this.requirementsOutputQuantity = resourceTagOutputResource.get(0).getQuantity();
            this.requirementsOutputType = resourceTagOutputResource.get(0).getType();
            ColorCLI colorResource = this.colorRequirementsType.get(this.requirementsOutputType);
            insertingOutputResourcesCard(this.requirementsOutputQuantity, colorResource); }
        else if(resourceTagOutputResource.size() == 2){
            this.requirementsOutputQuantity1 = resourceTagOutputResource.get(0).getQuantity();
            this.requirementsOutputType1 = resourceTagOutputResource.get(0).getType();
            ColorCLI colorResource1 = this.colorRequirementsType.get(this.requirementsOutputType1);
            this.requirementsOutputQuantity2 = resourceTagOutputResource.get(1).getQuantity();
            this.requirementsOutputType2 = resourceTagOutputResource.get(1).getType();
            ColorCLI colorResource2 = this.colorRequirementsType.get(this.requirementsOutputType2);
            insertingOutputResourcesCard(this.requirementsOutputQuantity1, colorResource1, this.requirementsOutputQuantity2, colorResource2);
        }
        else {
            this.requirementsOutputQuantity1 = resourceTagOutputResource.get(0).getQuantity();
            this.requirementsOutputType1 = resourceTagOutputResource.get(0).getType();
            ColorCLI colorResource1 = this.colorRequirementsType.get(this.requirementsOutputType1);
            this.requirementsOutputQuantity2 = resourceTagOutputResource.get(1).getQuantity();
            this.requirementsOutputType2 = resourceTagOutputResource.get(1).getType();
            ColorCLI colorResource2 = this.colorRequirementsType.get(this.requirementsOutputType2);
            this.requirementsOutputQuantity3 = resourceTagOutputResource.get(2).getQuantity();
            this.requirementsOutputType3 = resourceTagOutputResource.get(2).getType();
            ColorCLI colorResource3 = this.colorRequirementsType.get(this.requirementsOutputType3);
            insertingOutputResourcesCard(this.requirementsOutputQuantity1, colorResource1, this.requirementsOutputQuantity2, colorResource2, this.requirementsOutputQuantity3, colorResource3);
        }

    }

    private void loadIndexCard(Integer i){
        this.index = i;
        if(this.index < 10) {
            insertingIndex(index);
        }
        else if(this.index == 10){
            insertingIndex10();
        }
        else{
            insertingIndex11();
        }

    }

    private void insertingRequirementsCard(Integer graphicalQuantity, ColorCLI colorResource){
        cells[1][1] = "P";
        cells[1][2] = "R";
        cells[1][3] = "I";
        cells[1][4] = "C";
        cells[1][5] = "E";
        cells[1][7] = "" + graphicalQuantity;
        cells[1][8] = colorResource.escape() + "@" + ColorCLI.getRESET();

    }

    private void insertingRequirementsCard(Integer graphicalQuantity1, ColorCLI colorResource1,Integer graphicalQuantity2, ColorCLI colorResource2){
        cells[1][1] = "P";
        cells[1][2] = "R";
        cells[1][3] = "I";
        cells[1][4] = "C";
        cells[1][5] = "E";
        cells[1][7] = "" + graphicalQuantity1;
        cells[1][8] = colorResource1.escape() + "@" + ColorCLI.getRESET();
        cells[2][7] = "" + graphicalQuantity2;
        cells[2][8] = colorResource2.escape() + "@" + ColorCLI.getRESET();
    }

    private void insertingInputResourcesCard(Integer graphicalQuantity, ColorCLI colorResource){
        cells[4][2] = "" + graphicalQuantity;// + "->";
        cells[4][3] = colorResource.escape() + "@" + ColorCLI.getRESET();
    }

    private void insertingOutputResourcesCard(Integer graphicalQuantity, ColorCLI colorResource){
        cells[4][7] = "" + graphicalQuantity;
        cells[4][8] = colorResource.escape() + "@" + ColorCLI.getRESET();
    }

    private void insertingInputResourcesCard(Integer graphicalQuantity1, ColorCLI colorResource1, Integer graphicalQuantity2, ColorCLI colorResource2){

        cells[4][2] = "" + graphicalQuantity1;
        cells[4][3] = colorResource1.escape() + "@" + ColorCLI.getRESET();
        cells[5][2] = "" + graphicalQuantity2;
        cells[5][3] = colorResource2.escape() + "@" + ColorCLI.getRESET();

    }

    private void insertingOutputResourcesCard(Integer graphicalQuantity1, ColorCLI colorResource1, Integer graphicalQuantity2, ColorCLI colorResource2){
        cells[4][7] = "" + graphicalQuantity1;
        cells[4][8] = colorResource1.escape() + "@" + ColorCLI.getRESET();
        cells[5][7] = "" + graphicalQuantity2;
        cells[5][8] = colorResource2.escape() + "@" + ColorCLI.getRESET();
    }

    private void insertingOutputResourcesCard(Integer graphicalQuantity1, ColorCLI colorResource1, Integer graphicalQuantity2, ColorCLI colorResource2, Integer graphicalQuantity3, ColorCLI colorResource3){
        cells[4][7] = "" + graphicalQuantity1;
        cells[4][8] = colorResource1.escape() + "@" + ColorCLI.getRESET();
        cells[5][7] = "" + graphicalQuantity2;
        cells[5][8] = colorResource2.escape() + "@" + ColorCLI.getRESET();
        cells[6][7] = "" + graphicalQuantity3;
        cells[6][8] = colorResource3.escape() + "@" + ColorCLI.getRESET();
    }

    private void insertingIndex(Integer index){
        cells[7][1] = "I";
        cells[7][2] = "N";
        cells[7][3] = "D";
        cells[7][4] = "E";
        cells[7][5] = "X";
        cells[7][7] = "" + index;
    }

    private void insertingIndex10(){
        cells[7][1] = "I";
        cells[7][2] = "N";
        cells[7][3] = "D";
        cells[7][4] = "E";
        cells[7][5] = "X";
        cells[7][7] = "1";
        cells[7][8] = "0";
    }

    private void insertingIndex11(){
        cells[7][1] = "I";
        cells[7][2] = "N";
        cells[7][3] = "D";
        cells[7][4] = "E";
        cells[7][5] = "X";
        cells[7][7] = "1";
        cells[7][8] = "1";
    }

    private void insertingVictoryPointsCard(Integer victoryPoints){
        cells[8][1] = "P";
        cells[8][2] = "O";
        cells[8][3] = "I";
        cells[8][4] = "N";
        cells[8][5] = "T";
        cells[8][6] = "S";
        cells[8][8] = "" + victoryPoints;
    }

    private void insertingVictoryPointsCard10(){
        cells[8][1] = "P";
        cells[8][2] = "O";
        cells[8][3] = "I";
        cells[8][4] = "N";
        cells[8][5] = "T";
        cells[8][6] = "S";
        cells[8][8] = "1";
        cells[8][9] = "0";
    }

    private void insertingVictoryPointsCard11(){
        cells[8][1] = "P";
        cells[8][2] = "O";
        cells[8][3] = "I";
        cells[8][4] = "N";
        cells[8][5] = "T";
        cells[8][6] = "S";
        cells[8][8] = "1";
        cells[8][9] = "1";
    }

    private void insertingVictoryPointsCard12(){
        cells[8][1] = "P";
        cells[8][2] = "O";
        cells[8][3] = "I";
        cells[8][4] = "N";
        cells[8][5] = "T";
        cells[8][6] = "S";
        cells[8][8] = "1";
        cells[8][9] = "2";
    }

    private void insertingLevelCard(Integer level){
        cells[10][1] = "L";
        cells[10][2] = "E";
        cells[10][3] = "V";
        cells[10][4] = "E";
        cells[10][5] = "L";
        cells[10][7] = "" + level;
    }

    private void initCardLevel(){
        cardLevel.put(Level.ONE, 1);
        cardLevel.put(Level.TWO, 2);
        cardLevel.put(Level.THREE, 3);
    }

    private void initColorLevel(){
        cardColor.put(Color.GREEN, ColorCLI.ANSI_GREEN);
        cardColor.put(Color.BLUE, ColorCLI.ANSI_BLUE);
        cardColor.put(Color.YELLOW, ColorCLI.ANSI_BRIGHT_YELLOW);
        cardColor.put(Color.PURPLE, ColorCLI.ANSI_BRIGHT_PURPLE);
    }

    private void initCardRequirementsType() {
        colorRequirementsType.put(ResourceType.SERVANT, ColorCLI.ANSI_PURPLE);
        colorRequirementsType.put(ResourceType.COIN, ColorCLI.ANSI_YELLOW);
        colorRequirementsType.put(ResourceType.SHIELD, ColorCLI.ANSI_BLUE);
        colorRequirementsType.put(ResourceType.STONE, ColorCLI.ANSI_WHITE);
        colorRequirementsType.put(ResourceType.FAITH, ColorCLI.ANSI_RED);
        colorRequirementsType.put(ResourceType.UNDEFINED, ColorCLI.ANSI_BRIGHT_WHITE);
    }

    public void drawOneProductionCard() {
        for (int r = 0; r < MAX_VERT_TILES; r++) {
            System.out.println();
            for (int c = 0; c < MAX_HORIZ_TILES; c++) {
                System.out.print(cells[r][c]);
            }
        }
    }

    public static int getMaxVertTiles() {
        return MAX_VERT_TILES;
    }

    public static int getMaxHorizTiles() {
        return MAX_HORIZ_TILES;
    }

    public String[][] getCells() {
        return cells;
    }
}
