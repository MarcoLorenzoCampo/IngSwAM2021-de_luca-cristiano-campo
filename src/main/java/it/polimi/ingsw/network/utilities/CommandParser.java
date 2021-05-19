package it.polimi.ingsw.network.utilities;

import it.polimi.ingsw.enumerations.ResourceType;

/**
 * Validates outbound commands. If they are written following a specific format, they are sent
 * to be validated and eventually executed.
 */
public final class CommandParser {

    public static String parseCmd(String[] cmdMembers) {

        switch (cmdMembers.length) {

            case (1) :
                switch (cmdMembers[0]) {
                    case ("-help"):
                        return "HELP";
                    case ("END_TURN"):
                        return "END_TURN";
                    case ("CHECK_CARDS"):
                        return "CHECK_CARDS";
                    case ("CHECK_MARKET"):
                        return "CHECK_MARKET";


                    default : return "\nInvalid single word command.";
                }

            case (2) :
                switch(cmdMembers[0]) {
                    case("DISCARD_LEADER") :
                        try {
                            Integer.parseInt(cmdMembers[1]);
                        } catch (NumberFormatException e) {
                            return "\nInvalid DISCARD_LEADER argument.";
                        }
                        return "DISCARD_LEADER";

                    case("ACTIVATE_LEADER") :
                        try {
                            Integer.parseInt(cmdMembers[1]);
                        } catch (NumberFormatException e) {
                            return "\nInvalid ACTIVATE_LEADER argument.";
                        }
                        return "ACTIVATE_LEADER";

                    case("GET_RESOURCES") :
                        int line;
                        try {
                            line = Integer.parseInt(cmdMembers[1]);
                        } catch (NumberFormatException e) {
                            return "\nInvalid GET_RESOURCES argument.";
                        }
                        if(line < 0 || line > 6) {
                            return "\nPick a valid row/column from market.";
                        }
                        return "GET_RESOURCES";

                    case("DEPOSIT") :
                        try {
                            line = Integer.parseInt(cmdMembers[1]);
                        } catch (NumberFormatException e) {
                            return "\nInvalid DEPOSIT argument.";
                        }
                        if(line < 0 || line > 3) {
                            return "\nPick a valid resource to deposit.";
                        }
                        return "DEPOSIT_RESOURCE";
                        //return "DEPOSIT";

                    case("ACTIVATE_PRODUCTION") :
                        try {
                            line = Integer.parseInt(cmdMembers[1]);
                        } catch (NumberFormatException e) {
                            return "\nInvalid ACTIVATE_PRODUCTION argument.";
                        }
                        if(line < 0 || line > 2) {
                            return "\nPick a valid production index.";
                        }
                        return "ACTIVATE_PRODUCTION";

                    case("SOURCE"):
                        return cmdMembers[1];



                    default : return "\nInvalid two words command.";
                }

            case (3) :
                switch(cmdMembers[0]) {

                    case ("BUY_CARD"):
                        int i1;
                        int i2;

                        try {
                            i1 = Integer.parseInt(cmdMembers[1]);
                            i2 = Integer.parseInt(cmdMembers[2]);
                        } catch (NumberFormatException e) {
                            return "\nInvalid BUY_CARD arguments.";
                        }
                        if(i1 < 0 || i1 > 11) {
                            return "\nPick a valid card to buy.";
                        }
                        if(i2 < 0 || i2 > 2) {
                            return "\nPick a valid production slot.";
                        }
                        return "BUY_CARD";

                    case ("ACTIVATE_EXTRA_PRODUCTION") :
                        ResourceType o1;
                        try {
                            Integer.parseInt(cmdMembers[1]);
                        } catch (NumberFormatException e) {
                            return "\nInvalid extra production argument.";
                        }
                        try {
                            o1 = ResourceType.valueOf(cmdMembers[2]);
                        } catch (IllegalArgumentException e) {
                            return "\nInvalid output resource.";
                        }
                        if(o1.equals(ResourceType.FAITH) || o1.equals(ResourceType.UNDEFINED)) {
                            return "\nFaith can't be obtained this way.";
                        }
                        return "ACTIVATE_EXTRA_PRODUCTION";

                    default : return "\nInvalid three words command.";
                }

            case (4) :

                if(cmdMembers[0].equals("ACTIVATE_BASE_PRODUCTION")) {

                    try {
                        ResourceType.valueOf(cmdMembers[1]);
                        ResourceType.valueOf(cmdMembers[2]);
                        ResourceType.valueOf(cmdMembers[3]);

                    } catch (IllegalArgumentException e) {
                        return "\nInvalid input/output resource.";
                    }
                    return "ACTIVATE_BASE_PRODUCTION";
                } else {
                    return "\nInvalid four words command.";
                }

            default: return "UNKNOWN_COMMAND";
        }
    }
}
