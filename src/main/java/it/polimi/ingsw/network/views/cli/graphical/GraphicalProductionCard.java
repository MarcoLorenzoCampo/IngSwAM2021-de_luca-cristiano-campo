package it.polimi.ingsw.network.views.cli.graphical;



import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.views.cli.ColorCLI;

import it.polimi.ingsw.network.views.cli.constants.GraphicalResourceConstants;
import it.polimi.ingsw.network.views.cli.constants.LevelConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphicalProductionCard {

    private static final int MAX_VERT_TILES = 13; //rows.
    private static final int MAX_HORIZ_TILES = 10; //cols.

    private ProductionCard productionCard;

    private String tiles[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];
    private String level;
    private ColorCLI color;
    private int victoryPoints;
    private ArrayList<ResourceTag> requirements;
    private ResourceType requirementsType;
    private ResourceType requirementsType1;
    private ResourceType requirementsType2;
    private Integer requirementsQuantity;
    private Integer requirementsQuantity1;
    private Integer requirementsQuantity2;
    private ResourceType requirementsInputType;
    private ResourceType requirementsInputType1;
    private ResourceType requirementsInputType2;
    private ResourceType requirementsInputType3;
    private ResourceType requirementsOutputType;
    private ResourceType requirementsOutputType1;
    private ResourceType requirementsOutputType2;
    private ResourceType requirementsOutputType3;

    private Integer requirementsInputQuantity;
    private Integer requirementsInputQuantity1;
    private Integer requirementsInputQuantity2;
    private Integer requirementsInputQuantity3;
    private Integer requirementsOutputQuantity;
    private Integer requirementsOutputQuantity1;
    private Integer requirementsOutputQuantity2;
    private Integer requirementsOutputQuantity3;
    private ArrayList<ResourceTag> inputResources;
    private ArrayList<ResourceTag> outputResources;


    private Map<Level, String> cardLevel = new HashMap<>();
    private Map<Color, ColorCLI> cardColor = new HashMap<>();
    private Map<ResourceType, String> cardRequirementsType = new HashMap<>();




    public GraphicalProductionCard(ProductionCard productionCard) {
        this.productionCard = productionCard;
        borderBuilding();
        loadAvailableCard();
        initCardRequirementsType();
        initColorLevel();
        initCardLevel();
    }


    private void borderBuilding() {

        tiles[0][0] = "╭";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            tiles[0][c] = "-";
        }

        tiles[0][MAX_HORIZ_TILES - 1] = "╮";

        for (int r = 1; r < MAX_VERT_TILES - 1; r++) {
            tiles[r][0] = "|";
            for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
                tiles[r][c] = " ";
            }
            tiles[r][MAX_HORIZ_TILES - 1] = "|";
        }

        tiles[MAX_VERT_TILES - 1][0] = "╰";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            tiles[MAX_VERT_TILES - 1][c] = "-";
        }

        tiles[MAX_VERT_TILES - 1][MAX_HORIZ_TILES - 1] = "╯";

    }

    private void loadAvailableCard(){

        Level level = productionCard.getLevel();
        this.level = this.cardLevel.get(level);
        insertingLevelCard(this.level);

        Color color = productionCard.getColor();
        this.color = this.cardColor.get(color);

        this.victoryPoints = productionCard.getVictoryPoints();
        insertingVictoryPointsCard(this.victoryPoints);

        ArrayList<ResourceTag> resourceTagRequirements = productionCard.getRequirements();
        if(resourceTagRequirements.size()==1){
            this.requirementsQuantity = resourceTagRequirements.get(0).getQuantity();
            this.requirementsType = resourceTagRequirements.get(0).getType();
            String graphicalType = this.cardRequirementsType.get(this.requirementsType);
            insertingRequirementsCard(this.requirementsQuantity, graphicalType); }
        else{
            this.requirementsQuantity1 = resourceTagRequirements.get(0).getQuantity();
            this.requirementsType1 = resourceTagRequirements.get(0).getType();
            String graphicalType1 = this.cardRequirementsType.get(this.requirementsType);
            this.requirementsQuantity2 = resourceTagRequirements.get(1).getQuantity();
            this.requirementsType2 = resourceTagRequirements.get(1).getType();
            String graphicalType2 = this.cardRequirementsType.get(this.requirementsType);
            insertingRequirementsCard(this.requirementsQuantity1, graphicalType1, this.requirementsInputQuantity2, graphicalType2);
        }

        ArrayList<ResourceTag> resourceTagInputResource = productionCard.getInputResources();
        if(resourceTagInputResource.size() == 1){
            this.requirementsInputQuantity = resourceTagInputResource.get(0).getQuantity();
            this.requirementsInputType = resourceTagInputResource.get(0).getType();
            String graphicalInputType = this.cardRequirementsType.get(this.requirementsInputType);
            insertingInputResourcesCard(this.requirementsInputQuantity, graphicalInputType); }
        else if(resourceTagInputResource.size() == 2){
            this.requirementsInputQuantity1 = resourceTagInputResource.get(0).getQuantity();
            this.requirementsInputType1 = resourceTagInputResource.get(0).getType();
            String graphicalInputType1 = this.cardRequirementsType.get(this.requirementsInputType1);
            this.requirementsInputQuantity2 = resourceTagInputResource.get(1).getQuantity();
            this.requirementsInputType2 = resourceTagInputResource.get(1).getType();
            String graphicalInputType2 = this.cardRequirementsType.get(this.requirementsInputType2);
            insertingInputResourcesCard(this.requirementsInputQuantity1, graphicalInputType1, this.requirementsInputQuantity2, graphicalInputType2);
        }
        else {
            this.requirementsInputQuantity1 = resourceTagInputResource.get(0).getQuantity();
            this.requirementsInputType1 = resourceTagInputResource.get(0).getType();
            String graphicalInputType1 = this.cardRequirementsType.get(this.requirementsInputType1);
            this.requirementsInputQuantity2 = resourceTagInputResource.get(1).getQuantity();
            this.requirementsInputType2 = resourceTagInputResource.get(1).getType();
            String graphicalInputType2 = this.cardRequirementsType.get(this.requirementsInputType2);
            this.requirementsInputQuantity3 = resourceTagInputResource.get(2).getQuantity();
            this.requirementsInputType3 = resourceTagInputResource.get(2).getType();
            String graphicalInputType3 = this.cardRequirementsType.get(this.requirementsInputType3);
            insertingInputResourcesCard(this.requirementsInputQuantity1, graphicalInputType1, this.requirementsInputQuantity2, graphicalInputType2, this.requirementsInputQuantity3, graphicalInputType3);
        }


        ArrayList<ResourceTag> resourceTagOutputResource = productionCard.getOutputResources();
        if(resourceTagOutputResource.size() == 1){
            this.requirementsOutputQuantity = resourceTagOutputResource.get(0).getQuantity();
            this.requirementsOutputType = resourceTagOutputResource.get(0).getType();
            String graphicalOutputType = this.cardRequirementsType.get(this.requirementsOutputType);
            insertingOutputResourcesCard(this.requirementsOutputQuantity, graphicalOutputType); }
        else if(resourceTagOutputResource.size() == 2){
            this.requirementsOutputQuantity1 = resourceTagOutputResource.get(0).getQuantity();
            this.requirementsOutputType1 = resourceTagOutputResource.get(0).getType();
            String graphicalOutputType1 = this.cardRequirementsType.get(this.requirementsOutputType1);
            this.requirementsOutputQuantity2 = resourceTagOutputResource.get(1).getQuantity();
            this.requirementsInputType2 = resourceTagInputResource.get(1).getType();
            String graphicalOutputType2 = this.cardRequirementsType.get(this.requirementsOutputType2);
            insertingOutputResourcesCard(this.requirementsOutputQuantity1, graphicalOutputType1, this.requirementsOutputQuantity2, graphicalOutputType2);
        }
        else {
            this.requirementsOutputQuantity1 = resourceTagOutputResource.get(0).getQuantity();
            this.requirementsInputType1 = resourceTagInputResource.get(0).getType();
            String graphicalOutputType1 = this.cardRequirementsType.get(this.requirementsOutputType1);
            this.requirementsInputQuantity2 = resourceTagInputResource.get(1).getQuantity();
            this.requirementsOutputType2 = resourceTagOutputResource.get(1).getType();
            String graphicalOutputType2 = this.cardRequirementsType.get(this.requirementsOutputType2);
            this.requirementsOutputQuantity3 = resourceTagOutputResource.get(2).getQuantity();
            this.requirementsOutputType3 = resourceTagOutputResource.get(2).getType();
            String graphicalOutputType3 = this.cardRequirementsType.get(this.requirementsOutputType3);
            insertingOutputResourcesCard(this.requirementsOutputQuantity1, graphicalOutputType1, this.requirementsOutputQuantity2, graphicalOutputType2, this.requirementsOutputQuantity3, graphicalOutputType3);
        }



    }

    private void insertingRequirementsCard(Integer graphicalQuantity, String graphicalType){
        tiles[1][1] = "  ";
        tiles[2][1] = "P";
        tiles[2][2] = "R";
        tiles[2][3] = "I";
        tiles[2][4] = "C";
        tiles[2][5] = "E";
        tiles[2][7] = "" + graphicalQuantity;
        tiles[2][8] = " " + graphicalType;
        tiles[3][1] = "  ";
        tiles[4][1] = "  ";
    }

    private void insertingRequirementsCard(Integer graphicalQuantity1, String graphicalType1,Integer graphicalQuantity2, String graphicalType2){
        tiles[1][1] = "  ";
        tiles[2][1] = "P";
        tiles[2][2] = "R";
        tiles[2][3] = "I";
        tiles[2][4] = "C";
        tiles[2][5] = "E";
        tiles[2][7] = "" + graphicalQuantity1;
        tiles[2][8] = " " + graphicalType1;
        tiles[3][7] = "" + graphicalQuantity2;
        tiles[3][8] = " " + graphicalType2;
        tiles[4][1] = "  ";
    }

    private void insertingInputResourcesCard(Integer graphicalQuantity, String graphicalType){
        tiles[5][2] = "" + graphicalQuantity;// + "->";
        tiles[5][3] = graphicalType;
        tiles[6][1] = "  ";
        tiles[7][1] = "  ";
        tiles[8][1] = "  ";
    }

    private void insertingOutputResourcesCard(Integer graphicalQuantity, String graphicalType){
        tiles[5][7] = "" + graphicalQuantity;
        tiles[5][8] = graphicalType;
    }

    private void insertingInputResourcesCard(Integer graphicalQuantity1, String graphicalType1, Integer graphicalQuantity2, String graphicalType2){

        tiles[5][2] = "" + graphicalQuantity1;
        tiles[5][3] = graphicalType1;
        tiles[6][2] = "" + graphicalQuantity2;
        tiles[6][3] = graphicalType2;
        tiles[7][1] = "  ";
        tiles[8][1] = "  ";
    }

    private void insertingOutputResourcesCard(Integer graphicalQuantity1, String graphicalType1, Integer graphicalQuantity2, String graphicalType2){
        tiles[5][7] = "" + graphicalQuantity1;
        tiles[5][8] = graphicalType1;
        tiles[6][7] = "" + graphicalQuantity2;
        tiles[6][8] = graphicalType2;
    }

    private void insertingInputResourcesCard(Integer graphicalQuantity1, String graphicalType1, Integer graphicalQuantity2, String graphicalType2, Integer graphicalQuantity3, String graphicalType3){
        tiles[5][2] = "" + graphicalQuantity1;// + "╮";
        tiles[5][3] = graphicalType1;
        tiles[6][2] = "" + graphicalQuantity2;// + "|->";
        tiles[6][3] = graphicalType2;
        tiles[7][2] = "" + graphicalQuantity3;// + "╯";
        tiles[7][3] = graphicalType3;
        tiles[8][1] = "  ";
    }

    private void insertingOutputResourcesCard(Integer graphicalQuantity1, String graphicalType1, Integer graphicalQuantity2, String graphicalType2, Integer graphicalQuantity3, String graphicalType3){
        tiles[5][7] = "" + graphicalQuantity1;
        tiles[5][8] = graphicalType1;
        tiles[6][7] = "" + graphicalQuantity2;
        tiles[6][8] = graphicalType2;
        tiles[7][7] = "" + graphicalQuantity3;
        tiles[7][8] = graphicalType3;
    }

    private void insertingVictoryPointsCard(Integer graphicalVictoryPoints){
        tiles[9][1] = "P";
        tiles[9][2] = "O";
        tiles[9][3] = "I";
        tiles[9][4] = "N";
        tiles[9][5] = "T";
        tiles[9][6] = "S";
        tiles[9][7] = "  " + graphicalVictoryPoints;
        tiles[10][1] = "  ";
    }

    private void insertingLevelCard(String graphicalLevel){
        tiles[11][1] = "L";
        tiles[11][2] = "E";
        tiles[11][3] = "V";
        tiles[11][4] = "E";
        tiles[11][5] = "L";
        tiles[11][6] = "  " + graphicalLevel;
        tiles[12][1] = "  ";
    }

    private void initCardLevel(){
        cardLevel.put(Level.ONE, LevelConstants.levelOne);
        cardLevel.put(Level.TWO, LevelConstants.levelTwo);
        cardLevel.put(Level.THREE, LevelConstants.levelThree);
    }

    private void initColorLevel(){
        cardColor.put(Color.GREEN, ColorCLI.ANSI_GREEN);
        cardColor.put(Color.BLUE, ColorCLI.ANSI_BLUE);
        cardColor.put(Color.YELLOW, ColorCLI.ANSI_BRIGHT_YELLOW);
        cardColor.put(Color.PURPLE, ColorCLI.ANSI_BRIGHT_PURPLE);
    }

    private void initCardRequirementsType() {
        cardRequirementsType.put(ResourceType.SERVANT, GraphicalResourceConstants.servant);
        cardRequirementsType.put(ResourceType.COIN, GraphicalResourceConstants.coin);
        cardRequirementsType.put(ResourceType.SHIELD, GraphicalResourceConstants.shield);
        cardRequirementsType.put(ResourceType.STONE, GraphicalResourceConstants.stone);
        cardRequirementsType.put(ResourceType.FAITH, GraphicalResourceConstants.faith);
    }

    public void draw() {
        System.out.print(this.color.escape());
        for (int r = 0; r < MAX_VERT_TILES; r++) {
            System.out.println();
            for (int c = 0; c < MAX_HORIZ_TILES; c++) {
                System.out.print(tiles[r][c]);
            }
        }
    }

}
