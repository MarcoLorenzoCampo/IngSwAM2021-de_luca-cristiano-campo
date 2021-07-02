package it.polimi.ingsw.views.cli.graphical;


import it.polimi.ingsw.views.cli.ColorCLI;

public class GraphicalLegend {
    private static final int MAX_VERT_TILES = 8; //rows.
    private static final int MAX_HORIZ_TILES = 12; //cols.

    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    public GraphicalLegend(){
        cellBuilding();
        loadLegend();
    }

    private void cellBuilding() {

        cells[0][0] = "";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[0][c] = "";
        }

        cells[0][MAX_HORIZ_TILES - 1] = "";

        for (int r = 1; r < MAX_VERT_TILES - 1; r++) {
            cells[r][0] = "";
            for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
                cells[r][c] = " ";
            }
            cells[r][MAX_HORIZ_TILES-1] = "";
        }

        cells[MAX_VERT_TILES - 1][0] =  "";
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[MAX_VERT_TILES - 1][c] =  "";
        }

        cells[MAX_VERT_TILES - 1][MAX_HORIZ_TILES - 1] = "";

    }
    private void loadLegend(){
        cells[0][1] = ColorCLI.ANSI_YELLOW.escape() + " @ COIN" + ColorCLI.getRESET();

        cells[2][1] = ColorCLI.ANSI_PURPLE.escape() + " @ SERVANT" + ColorCLI.getRESET();

        cells[4][1] = ColorCLI.ANSI_WHITE.escape() + " @ STONE" + ColorCLI.getRESET();
        cells[6][1] = ColorCLI.ANSI_BLUE.escape() + " @ SHIELD" + ColorCLI.getRESET();
    }

    public String[][] getCells() {
        return cells;
    }

    public static int getMaxVertTiles() {
        return MAX_VERT_TILES;
    }

    public static int getMaxHorizTiles() {
        return MAX_HORIZ_TILES;
    }
}