package it.polimi.ingsw.network.views.cli;

/**
 * Titles and default messages used in the CLI.
 */
public class UsefulStrings {

    public static String getLogo() {
        return logo;
    }

    public static String getGreetings() {
        return greetings;
    }

    public static String getDisgracefulEnding() {
        return disgracefulEnding;
    }

    public static String getWhatToDo() {
        return whatToDo;
    }

    private static final String logo =
                 "\n  ██████   ██████                    █████                                                                \n" +
                         "░░██████ ██████                    ░░███                                                                 \n" +
                         " ░███░█████░███   ██████    █████  ███████    ██████  ████████   █████                                   \n" +
                         " ░███░░███ ░███  ░░░░░███  ███░░  ░░░███░    ███░░███░░███░░███ ███░░                                    \n" +
                         " ░███ ░░░  ░███   ███████ ░░█████   ░███    ░███████  ░███ ░░░ ░░█████                                   \n" +
                         " ░███      ░███  ███░░███  ░░░░███  ░███ ███░███░░░   ░███      ░░░░███                                  \n" +
                         " █████     █████░░████████ ██████   ░░█████ ░░██████  █████     ██████                                   \n" +
                         "░░░░░     ░░░░░  ░░░░░░░░ ░░░░░░     ░░░░░   ░░░░░░  ░░░░░     ░░░░░░                                    \n" +
                         "    ███████       ██████                                                                                 \n" +
                         "  ███░░░░░███    ███░░███                                                                                \n" +
                         " ███     ░░███  ░███ ░░░                                                                                 \n" +
                         "░███      ░███ ███████                                                                                   \n" +
                         "░███      ░███░░░███░                                                                                    \n" +
                         "░░███     ███   ░███                                                                                     \n" +
                         " ░░░███████░    █████                                                                                    \n" +
                         "   ░░░░░░░     ░░░░░                                                                                     \n" +
                         " ███████████                                  ███                                                        \n" +
                         "░░███░░░░░███                                ░░░                                                         \n" +
                         " ░███    ░███   ██████  ████████    ██████   ████   █████   █████   ██████   ████████    ██████   ██████ \n" +
                         " ░██████████   ███░░███░░███░░███  ░░░░░███ ░░███  ███░░   ███░░   ░░░░░███ ░░███░░███  ███░░███ ███░░███\n" +
                         " ░███░░░░░███ ░███████  ░███ ░███   ███████  ░███ ░░█████ ░░█████   ███████  ░███ ░███ ░███ ░░░ ░███████ \n" +
                         " ░███    ░███ ░███░░░   ░███ ░███  ███░░███  ░███  ░░░░███ ░░░░███ ███░░███  ░███ ░███ ░███  ███░███░░░  \n" +
                         " █████   █████░░██████  ████ █████░░████████ █████ ██████  ██████ ░░████████ ████ █████░░██████ ░░██████ \n" +
                         "░░░░░   ░░░░░  ░░░░░░  ░░░░ ░░░░░  ░░░░░░░░ ░░░░░ ░░░░░░  ░░░░░░   ░░░░░░░░ ░░░░ ░░░░░  ░░░░░░   ░░░░░░  \n" +
                         "                                                                                                         \n" +
                         "                                                                                                         ";

    private static final String greetings = "\n---------------------------------------------------------------------------------------------------------" +
            "\n\nWelcome to Masters Of Renaissance! This Version was implemented by:" +
                    "\nthe almighty Marco Lorenzo Campo , Alessandro De Luca and Mario Cristiano." +
                    "\nHope you enjoy <3 !" +
            "\n\n---------------------------------------------------------------------------------------------------------";

    private static final String disgracefulEnding =
                        ".-.-. .-.-. .-.-. .-.-. .-.-.  \n" +
                        "'. S )'. O )'. R )'. R )'. Y ) \n" +
                        "  ).'   ).'   ).'   ).'   ).'  \n" +
                        "                               ";

    private static final String whatToDo =
            "-------------------------------------------------------------------------------------------------------------" +
                    "\nHere's a complete list of the accepted commands:" +
                    "\n - 'DISCARD_LEADER <int>': Discards one of your leader cards (Requires a valid card index);" +
                    "\n - 'ACTIVATE_LEADER <int>': Places one of your leader cards (Requires a valid card index);" +
                    "\n - 'GET_RESOURCES <int>': Gets resources from the market (Requires and index form 0 to 6);" +
                    "\n - 'BUY_CARD <int>': Buys an available card (Requires a valid card index and a valid production slot index);" +
                    "\n - 'ACTIVATE_BASE_PRODUCTION <ResourceType> <ResourceType> <ResourceType>': " +
                    "\n     Activates the base production (asks you 2 input resources and 1 output resource);" +
                    "\n - 'CARD_PRODUCTION': " +
                    "\n - 'PEEK_<enemy nickname>': Checks on one of your enemies;" +
                    "\n - 'CHECK_MARKET': For an updated ResourceMarket;" +
                    "\n - 'CHECK_CARDS': For an updated ProductionCardsMarket;" +
                    "\n - 'CHECK_LEADERS': For an updated LeaderCards list;" +
                    "\n - 'CHECK_PRODUCTIONS': For an updated ProductionBoard;" +
                    "\n-------------------------------------------------------------------------------------------------------------\n";
}
