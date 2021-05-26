package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.views.cli.LightweightModel;

import java.util.List;

public class GraphicalLeaderCards {

    private final LightweightModel lightweightModel;

    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12 ; //columns.
    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES * 5];
    private List<LeaderCard> leaderCards;

    public GraphicalLeaderCards(List<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
        this.lightweightModel = new LightweightModel();
        updatingGraphicalLeaderCards();
    }

    public void updatingGraphicalLeaderCards() {


        int k2 = 0;

        lightweightModel.setLeaderCards(leaderCards);
        for (LeaderCard leaderCard: leaderCards) {
            switch (leaderCard.getEffectType()) {
                case DISCOUNT:
                    GraphicalDiscountLeaderCard graphicalDiscountLeaderCard = new GraphicalDiscountLeaderCard(leaderCard);
                    for (int j = 0; j < graphicalDiscountLeaderCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < graphicalDiscountLeaderCard.getMaxHorizTiles(); k++) {
                            cells[j][k + k2] = (graphicalDiscountLeaderCard.getCells())[j][k];
                        }
                    } break;
                case EXTRA_INVENTORY:
                    GraphicalExtraInventoryLeaderCard graphicalExtraInventoryLeaderCard = new GraphicalExtraInventoryLeaderCard(leaderCard);
                    for (int j = 0; j < graphicalExtraInventoryLeaderCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < graphicalExtraInventoryLeaderCard.getMaxHorizTiles(); k++) {
                            cells[j][k + k2] = (graphicalExtraInventoryLeaderCard.getCells())[j][k];
                        }
                    } break;
                case EXTRA_PRODUCTION:
                    GraphicalExtraProductionLeaderCard graphicalExtraProductionLeaderCard = new GraphicalExtraProductionLeaderCard(leaderCard);
                    for (int j = 0; j < graphicalExtraProductionLeaderCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < graphicalExtraProductionLeaderCard.getMaxHorizTiles(); k++) {
                            cells[j][k + k2] = (graphicalExtraProductionLeaderCard.getCells())[j][k];
                        }
                    } break;
                case MARBLE_EXCHANGE:
                    GraphicalMarbleExchangeLeaderCard graphicalMarbleExchangeLeaderCard = new GraphicalMarbleExchangeLeaderCard(leaderCard);
                    for (int j = 0; j < graphicalMarbleExchangeLeaderCard.getMaxVertTiles(); j++) {
                        for (int k = 0; k < graphicalMarbleExchangeLeaderCard.getMaxHorizTiles(); k++) {
                            cells[j][k + k2] = (graphicalMarbleExchangeLeaderCard.getCells())[j][k];
                        }
                    } break;

            } k2 = k2 + 12;
            GraphicalLegend graphicalLegend = new GraphicalLegend();
            for (int j = 0; j < GraphicalLegend.getMaxVertTiles(); j++) {
                for (int k = 0; k < GraphicalLegend.getMaxHorizTiles(); k++) {
                    cells[j][k + k2] = (graphicalLegend.getCells())[j][k];
                }
            }
        }
    }

    public void draw() {

        for (int r = 0; r < MAX_VERT_TILES; r++) {
            System.out.println();
            for (int c = 0; c < (leaderCards.size() + 1) * MAX_HORIZ_TILES ; c++) {
                System.out.print(cells[r][c]);
            }
        }
    }


}