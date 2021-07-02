package it.polimi.ingsw.views.cli.graphical;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.views.cli.LightweightModel;

import java.util.List;

public class GraphicalProductionCardsMarket {

    private final LightweightModel lightweightModel;

    private static final int MAX_VERT_TILES = 12; //rows.
    private static final int MAX_HORIZ_TILES = 12 ; //cols.
    private final String[][] cells = new String[MAX_VERT_TILES * 3][MAX_HORIZ_TILES * 4];
    private final List<ProductionCard> productionCards;

    public GraphicalProductionCardsMarket(List<ProductionCard> productionCards) {
        this.productionCards = productionCards;
        this.lightweightModel = new LightweightModel();
        updatingGraphicalProductionCards();
    }

    public void updatingGraphicalProductionCards() {

        int jj = 0;
        int ll = 0;
        int mm = 0;
        int pp = 0;

        lightweightModel.setReducedAvailableCards(productionCards);

        for(int i = 0; i < 12; i++){
            GraphicalProductionCardBoard graphicalProductionCardBoard = new GraphicalProductionCardBoard();
            if (jj<48){
                for (int j = 0; j < GraphicalProductionCardBoard.getMaxVertTiles(); j++) {
                    for (int k = 0; k < GraphicalProductionCardBoard.getMaxHorizTiles(); k++) {
                        cells[j][k + ll] = (graphicalProductionCardBoard.getCells())[j][k];
                    }
                } ll = ll + 12;
            }
            else if ( jj>=48 && jj <96){
                for (int j = 0; j < GraphicalProductionCardBoard.getMaxVertTiles(); j++) {
                    for (int k = 0; k < GraphicalProductionCardBoard.getMaxHorizTiles(); k++) {
                        cells[j + 12][k + mm] = (graphicalProductionCardBoard.getCells())[j][k];
                    }
                } mm = mm + 12;
            }
            else{
                for (int j = 0; j < GraphicalProductionCardBoard.getMaxVertTiles(); j++) {
                    for (int k = 0; k < GraphicalProductionCardBoard.getMaxHorizTiles(); k++) {
                        cells[j + 24][k + pp] = (graphicalProductionCardBoard.getCells())[j][k];
                    }
                } pp = pp + 12;
            }
            jj = jj + 12;
        }

        for (int i = 0; i < productionCards.size(); i++ ) {
            GraphicalProductionCard graphicalProductionCard = new GraphicalProductionCard(productionCards.get(i), i);
            if(productionCards.get(i).getLevel() == Level.ONE) {
                if(productionCards.get(i).getColor() == Color.GREEN) {
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j][k] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else if(productionCards.get(i).getColor() == Color.BLUE){
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j][k + 12] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else if(productionCards.get(i).getColor() == Color.YELLOW){
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j][k + 24] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else{
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j][k + 36] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
            }
            else if ( productionCards.get(i).getLevel() == Level.TWO) {
                if(productionCards.get(i).getColor() == Color.GREEN) {
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j + 12][k] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else if(productionCards.get(i).getColor() == Color.BLUE){
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j + 12][k + 12] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else if(productionCards.get(i).getColor() == Color.YELLOW){
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j + 12][k + 24] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else{
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j + 12][k + 36] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
            }
            else {
                if(productionCards.get(i).getColor() == Color.GREEN) {
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j + 24][k] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else if(productionCards.get(i).getColor() == Color.BLUE){
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j + 24][k + 12] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else if(productionCards.get(i).getColor() == Color.YELLOW){
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j + 24][k + 24] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
                else{
                    for (int j = 0; j < GraphicalProductionCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < GraphicalProductionCard.getMaxHorizTiles(); k++) {
                            cells[j + 24][k + 36] = (graphicalProductionCard.getCells())[j][k];
                        }
                    }
                }
            }
        }



    }


    public void draw() {

        for (int r = 0; r < MAX_VERT_TILES * 3; r++) {
            System.out.println();
            for (int c = 0; c < MAX_HORIZ_TILES * 4 ; c++) {
                System.out.print(cells[r][c]);
            }
        }
    }


}