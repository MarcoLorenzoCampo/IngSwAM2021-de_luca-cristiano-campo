package it.polimi.ingsw.network.views.cli;

public class Logo {

    public static String getLogo() {
        return logo;
    }

    public static String getGreetings() {
        return greetings;
    }

    public static String getDisgracefulEnding() {
        return disgracefulEnding;
    }

    private static final String logo =
                 "\n.  ..__. __..___..___.__  __.        .__..___        .__ .___.  ..__.._. __. __..__..  . __ .___\n" +
                         "|\\/|[__](__   |  [__ [__)(__         |  |[__         [__)[__ |\\ |[__] | (__ (__ [__]|\\ |/  `[__ \n" +
                         "|  ||  |.__)  |  [___|  \\.__)        |__||           |  \\[___| \\||  |_|_.__).__)|  || \\|\\__.[___";

    private static final String greetings =
            "\n\nWelcome to Masters Of Renaissance! This Version was implemented by:" +
                    "\nMarco Lorenzo Campo, Alessandro De Luca, Mario Cristiano" +
                    "\nHope you enjoy :)";

    private static final String disgracefulEnding =
                        ".-.-. .-.-. .-.-. .-.-. .-.-.  \n" +
                        "'. S )'. O )'. R )'. R )'. Y ) \n" +
                        "  ).'   ).'   ).'   ).'   ).'  \n" +
                        "                               ";

}
