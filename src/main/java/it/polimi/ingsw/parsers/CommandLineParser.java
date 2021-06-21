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

        int port = 0;

        if(args.length != 2) return false;

        if(args[0].equalsIgnoreCase("-port")) {

            try {
                port = Integer.parseInt(args[1]);
            } catch(NumberFormatException nfe) {
                return false;
            }
        }

        return port >= 1024;
    }

    /**
     * Realizes the parsing of cmd line arguments (if they've been validated)
     * @param args: users arguments from main.
     * @return: ServerConfigPOJO {@link ServerConfigPOJO} containing parsed data.
     */
    public static ServerConfigPOJO parseUserArgs(String[] args) {

        ServerConfigPOJO customConfig = new ServerConfigPOJO();

        customConfig.setPort(Integer.parseInt(args[1]));

        return customConfig;
    }
}
