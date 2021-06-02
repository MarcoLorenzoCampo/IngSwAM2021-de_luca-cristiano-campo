package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.model.faithtrack.FaithTrack;

public class GraphicalFaithTrack {
    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12 ; //cols.
    private final String cells[][] = new String[MAX_VERT_TILES * 5][MAX_HORIZ_TILES * 5];
    private FaithTrack faithTrack;

    public GraphicalFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
        updatingGraphicalFaithTrackTiles();
    }

    public void updatingGraphicalFaithTrackTiles() {

        int count = 0;
        int k1 = 0;
        int k2 = 0;
        int k3 = 0;
        int k4 = 0;
        int k5 = 0;

        for (int i = 0; i < 25 ; i++) {
            GraphicalFaithTrackTile graphicalFaithTrackTile = new GraphicalFaithTrackTile(faithTrack.getFaithTrack().get(i), faithTrack.getFaithMarker(), faithTrack.getFaithTrack().get(i).getVaticanSpace());
            if(count < 60) {
                for (int j = 0; j < graphicalFaithTrackTile.getMaxVertTiles(); j++) {
                    for (int k = 0; k < graphicalFaithTrackTile.getMaxHorizTiles(); k++) {
                        cells[j][k + k1] = (graphicalFaithTrackTile.getCells())[j][k];
                    }
                } k1 = k1 + 12;
            }
            else if (count < 120){
                for (int j = 0; j < graphicalFaithTrackTile.getMaxVertTiles(); j++) {
                    for (int k = 0; k < graphicalFaithTrackTile.getMaxHorizTiles(); k++) {
                        cells[j + 8][k + k2] = (graphicalFaithTrackTile.getCells())[j][k];
                    }
                } k2 = k2 + 12;
            }
            else if(count < 180){
                for (int j = 0; j < graphicalFaithTrackTile.getMaxVertTiles(); j++) {
                    for (int k = 0; k < graphicalFaithTrackTile.getMaxHorizTiles(); k++) {
                        cells[j + 16][k + k3] = (graphicalFaithTrackTile.getCells())[j][k];
                    }
                } k3 = k3 + 12;
            }
            else if (count < 240){
                for (int j = 0; j < graphicalFaithTrackTile.getMaxVertTiles(); j++) {
                    for (int k = 0; k < graphicalFaithTrackTile.getMaxHorizTiles(); k++) {
                        cells[j + 24][k + k4] = (graphicalFaithTrackTile.getCells())[j][k];
                    }
                } k4 = k4 + 12;
            }
            else{
                for (int j = 0; j < graphicalFaithTrackTile.getMaxVertTiles(); j++) {
                    for (int k = 0; k < graphicalFaithTrackTile.getMaxHorizTiles(); k++) {
                        cells[j + 32][k + k5] = (graphicalFaithTrackTile.getCells())[j][k];
                    }
                } k5 = k5 + 12;
            }
            count = count + 12;
        }
    }

    public void draw() {
        //System.out.print(this.colorCard.escape());
        for (int r = 0; r < MAX_VERT_TILES * 5; r++) {
            System.out.println();
            for (int c = 0; c < MAX_HORIZ_TILES * 5 ; c++) {
                System.out.print(cells[r][c]);
            }
        }
    }


}
