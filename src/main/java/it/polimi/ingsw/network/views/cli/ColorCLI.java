package it.polimi.ingsw.network.views.cli;

/**
 * Enum with the Codes of the colors used to fill the cli.
 */
public enum ColorCLI {
    //Color end string, color reset
    RESET("\033[0m"),
    CLEAR("\033[H\033[2J");

    private final String colorCode;

    ColorCLI(String colorCode) {
        this.colorCode = colorCode;
    }
}
