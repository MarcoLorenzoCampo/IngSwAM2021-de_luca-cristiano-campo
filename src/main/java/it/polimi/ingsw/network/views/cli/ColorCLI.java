package it.polimi.ingsw.network.views.cli;

/**
 * Enum with the Codes of the colors used to fill the cli.
 */
public enum ColorCLI {
    //Color end string
    CLEAR("\033[H\033[2J"),

    ANSI_BLACK("\u001B[30m"),
    ANSI_RED  ("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE ("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_CYAN ("\u001B[36m"),
    ANSI_WHITE("\u001B[37m"),
    ANSI_BRIGHT_BLACK("\u001B[90m"),
    ANSI_BRIGHT_RED  ("\u001B[91m"),
    ANSI_BRIGHT_GREEN("\u001B[92m"),
    ANSI_BRIGHT_YELLOW("\u001B[93m"),
    ANSI_BRIGHT_BLUE ("\u001B[94m"),
    ANSI_BRIGHT_PURPLE("\u001B[95m"),
    ANSI_BRIGHT_CYAN ("\u001B[96m"),
    ANSI_BRIGHT_WHITE("\u001B[97m");

    public static final String RESET = "\u001B[0m";

    private String escape;

    public static String getRESET() {
        return RESET;
    }

    ColorCLI(String escape) {
        this.escape = escape;
    }
    public String escape(){
        return escape;
    }
}
