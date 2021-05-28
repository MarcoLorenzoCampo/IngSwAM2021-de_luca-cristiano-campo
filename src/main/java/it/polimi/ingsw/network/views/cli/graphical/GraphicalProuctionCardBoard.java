package it.polimi.ingsw.network.views.cli.graphical;

import it.polimi.ingsw.network.views.cli.ColorCLI;

public class GraphicalProuctionCardBoard {

    private static final int MAX_VERT_TILES = 12; //rows.
    private static final int MAX_HORIZ_TILES = 12; //cols.




    private String cells[][] = new String[MAX_VERT_TILES][MAX_HORIZ_TILES];

    public GraphicalProuctionCardBoard(){
        updatingGraphicalBoard();
    }

    private void updatingGraphicalBoard() {

        cells[0][0] = ColorCLI.ANSI_WHITE.escape() + "╭" + ColorCLI.getRESET();
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[0][c] = ColorCLI.ANSI_WHITE.escape() + "-" + ColorCLI.getRESET();
        }

        cells[0][MAX_HORIZ_TILES - 1] = ColorCLI.ANSI_WHITE.escape() + "╮" + ColorCLI.getRESET();

        for (int r = 1; r < MAX_VERT_TILES - 1; r++) {
            cells[r][0] = ColorCLI.ANSI_WHITE.escape() + "|" + ColorCLI.getRESET();
            for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
                cells[r][c] =  " " ;
            }
            cells[r][MAX_HORIZ_TILES - 1] = ColorCLI.ANSI_WHITE.escape() + "|" + ColorCLI.getRESET();
        }

        cells[MAX_VERT_TILES - 1][0] = ColorCLI.ANSI_WHITE.escape() + "╰" + ColorCLI.getRESET();
        for (int c = 1; c < MAX_HORIZ_TILES - 1; c++) {
            cells[MAX_VERT_TILES - 1][c] = ColorCLI.ANSI_WHITE.escape() + "-" + ColorCLI.getRESET();
        }

        cells[MAX_VERT_TILES - 1][MAX_HORIZ_TILES - 1] = ColorCLI.ANSI_WHITE.escape() + "╯" + ColorCLI.getRESET();

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
