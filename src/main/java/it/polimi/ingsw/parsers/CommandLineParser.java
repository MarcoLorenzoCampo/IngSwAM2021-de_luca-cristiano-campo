package it.polimi.ingsw.parsers;

import it.polimi.ingsw.network.utilities.ServerConfigPOJO;

/**
 * Utility class to parse cmd line:
 *  user@user:$ Class.java -port <port#> -<game_mode>
 */

public final class CommandLineParser {

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

    public static ServerConfigPOJO parseUserArgs(String[] args) {

        ServerConfigPOJO customConfig = new ServerConfigPOJO();

        if(args.length != 3) return null;

        if(args[0].equalsIgnoreCase("-port")) {

            try {
                customConfig.setPort(Integer.parseInt(args[1]));
            } catch(NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }

        if(args[2].equalsIgnoreCase("-singlePlayer")
                || args[2].equalsIgnoreCase("-multiPlayer")) {

            customConfig.setGameMode(args[2]);
        } else {
            return null;
        }

        return customConfig;
    }
}
