package it.polimi.ingsw.parsers;

import it.polimi.ingsw.network.utilities.ServerConfigPOJO;

/**
 * Utility class to parse cmd line:
 *  user@user:$ Class.java -port <port#> -<game_mode>
 */

public final class CommandLineParser {

    /**
     * Validates if the cmd args can be used to build a ServerConfigPOJO object.
     */
    public  static boolean CmdValidator(String[] args) {

        if(args.length != 3) return false;

        if(args[0].equalsIgnoreCase("-port")) {

            try {
                Integer.parseInt(args[1]);
            } catch(NumberFormatException nfe) {
                return false;
            }
        } else {
            return false;
        }

        return args[2].equalsIgnoreCase("-singlePlayer")
                || args[2].equalsIgnoreCase("-multiPlayer");
    }

    /**
     * Realizes the parsing of cmd line arguments (if they've been validated)
     * @param args: users arguments from main.
     * @return: ServerConfigPOJO {@link ServerConfigPOJO} containing parsed data.
     */
    public static ServerConfigPOJO parseUserArgs(String[] args) {

        ServerConfigPOJO customConfig = new ServerConfigPOJO();

        customConfig.setPort(Integer.parseInt(args[1]));
        customConfig.setGameMode(args[2]);

        return customConfig;
    }
}
