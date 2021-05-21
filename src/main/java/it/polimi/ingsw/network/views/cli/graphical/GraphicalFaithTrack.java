package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.model.faithtrack.FaithTrack;

public class GraphicalFaithTrack {
    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12 ; //cols.
    private final String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES * 25];
    private FaithTrack faithTrack;

    public GraphicalFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
        updatingGraphicalFaithTrackTiles();
    }

    public void updatingGraphicalFaithTrackTiles() {
        int k2 = 0;
        for (int i = 0; i < 25; i++) {
            GraphicalFaithTrackTile graphicalFaithTrackTile = new GraphicalFaithTrackTile(faithTrack.getFaithTrack().get(i), faithTrack.getFaithMarker(), faithTrack.getFaithTrack().get(i).getVaticanSpace());
            for(int j = 0; j < graphicalFaithTrackTile.getMaxVertTiles(); j++){
                for(int k = 0; k < graphicalFaithTrackTile.getMaxHorizTiles(); k++){
                    cells[j][k + k2] = (graphicalFaithTrackTile.getCells())[j][k];
                }
            }
            k2 = k2 + 12;
        }
    }

    public void draw() {
        //System.out.print(this.colorCard.escape());
        for (int r = 0; r < MAX_VERT_TILES; r++) {
            System.out.println();
            for (int c = 0; c < MAX_HORIZ_TILES * 25 ; c++) {
                System.out.print(cells[r][c]);
            }
        }
    }


}
