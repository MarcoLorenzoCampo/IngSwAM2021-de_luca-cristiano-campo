package it.polimi.ingsw.network.views.cli;

/**
 * Titles and default messages used in the CLI.
 */
public class UsefulStrings {

    private static final String logo =
                 "\n    \\  |               |                                              \n" +
                         "  |\\/ |   _` |   __|  __|   _ \\   __|  __|                           \n" +
                         "  |   |  (   | \\__ \\  |     __/  |   \\__ \\                           \n" +
                         " _|  _| \\__,_| ____/ \\__| \\___| _|   ____/                           \n" +
                         "   _ \\    _|                                                         \n" +
                         "  |   |  |                                                           \n" +
                         "  |   |  __|                                                         \n" +
                         " \\___/  _|                                                           \n" +
                         "   _ \\                      _)                                       \n" +
                         "  |   |   _ \\  __ \\    _` |  |   __|   __|   _` |  __ \\    __|   _ \\ \n" +
                         "  __ <    __/  |   |  (   |  | \\__ \\ \\__ \\  (   |  |   |  (      __/ \n" +
                         " _| \\_\\ \\___| _|  _| \\__,_| _| ____/ ____/ \\__,_| _|  _| \\___| \\___|";

    private static final String greetings =
            "\n‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗" +
            "\n\nWelcome to Masters Of Renaissance! This Version was implemented by:" +
                    "\nthe almighty Marco Lorenzo Campo , Alessandro De Luca and Mario Cristiano." +
                    "\nHope you enjoy <3 !" +
            "\n\n‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗";

    private static final String unexpectedEnding =
                        ".-.-. .-.-. .-.-. .-.-. .-.-.  \n" +
                        "'. S )'. O )'. R )'. R )'. Y ) \n" +
                        "  ).'   ).'   ).'   ).'   ).'  \n" +
                        "                               ";

    private static final String whatToDo =
            "\n\n‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗" +
                    "\n\nHere's a complete list of the accepted commands:\n" +
                    "\n - 'DISCARD_LEADER <int>': Discards one of your leader cards (Requires a valid card index)." +
                    "\n - 'ACTIVATE_LEADER <int>': Places one of your leader cards (Requires a valid card index)." +
                    "\n - 'GET_RESOURCES <int>': Gets resources from the market (Requires and index form 0 to 6)." +
                    "\n - 'BUY_CARD <int> <int>': Buys an available card (Requires card index and production slot index)." +
                    "\n - 'ACTIVATE_BASE_PRODUCTION <ResourceType> <ResourceType> <ResourceType>': Activates the base production." +
                    "\n - 'ACTIVATE_PRODUCTION <int>': Activates a production card (Requires a production slot index)." +
                    "\n - 'ACTIVATE_EXTRA_PRODUCTION <int>': Activates a leader production (Requires the correct index)." +
                    "\n - 'CHECK_MARKET': For an updated version of the ResourceMarket." +
                    "\n - 'CHECK_CARDS': For an updated version of the ProductionCardsMarket." +
                    "\n - 'CHECK_LEADERS': For an updated list of your LeaderCards." +
                    "\n - 'CHECK_PRODUCTIONS': For an updated version of your ProductionBoard." +
                    "\n - 'CHECK_INVENTORY': To get an updated version of your STRONGBOX and WAREHOUSE." +
                    "\n - 'CHECK_TRACK': To get and updated version of your faith track." +
                    "\n - 'PEEK <enemy nickname>': Checks on one of your enemies." +
                    "\n               What you can see: Active Leaders, Faith Track position, their total resources owned." +
                    "\n\n‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗‗\n\n";

    public static String getLogo() {
        return logo;
    }

    public static String getGreetings() {
        return greetings;
    }

    public static String getDisgracefulEnding() {
        return unexpectedEnding;
    }

    public static String getWhatToDo() {
        return whatToDo;
    }
}