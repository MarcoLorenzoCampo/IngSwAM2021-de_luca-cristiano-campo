package it.polimi.ingsw.network.utilities;

import java.util.Arrays;

/**
 * Utility class to validate the network information given by the player.
 */
public final class NetworkInfoValidator {

    /**
     * Simple port validation
     * @param port: port to validate
     * @return boolean output
     */
    public static boolean isPortValid(int port) {

        return !(port < 1024 || port >= 65535);
    }

    /**
     * Validates Ip address integrity and values.
     * @param IPAddress: address to validate.
     * @return boolean output.
     */
    public static boolean isIPAddressValid(String IPAddress) {

        if(IPAddress == null) {
            return false;
        }

        String[] ipMembers = IPAddress.split("\\.");

        if (ipMembers.length != 4) {
            return false;
        }

        try {
            return Arrays.stream(ipMembers)
                    .filter(s -> s.length() >= 1)
                    .map(Integer::parseInt)
                    .filter(i -> (i >= 0 && i <= 255))
                    .count() == 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
