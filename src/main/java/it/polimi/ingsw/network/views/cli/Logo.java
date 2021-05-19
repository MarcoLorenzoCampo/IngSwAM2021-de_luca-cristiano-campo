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
                 "\n ___  ___  ___   __  ______  ____ ____       ___    ____    ____   ____ __  __  ___  __  __   __   ___  __  __   ___  ____\n" +
                         " ||\\\\//|| // \\\\ (( \\ | || | ||    || \\\\     // \\\\  ||       || \\\\ ||    ||\\ || // \\\\ || (( \\ (( \\ // \\\\ ||\\ ||  //   ||   \n" +
                         " || \\/ || ||=||  \\\\    ||   ||==  ||_//    ((   )) ||==     ||_// ||==  ||\\\\|| ||=|| ||  \\\\   \\\\  ||=|| ||\\\\|| ((    ||== \n" +
                         " ||    || || || \\_))   ||   ||___ || \\\\     \\\\_//  ||       || \\\\ ||___ || \\|| || || || \\_)) \\_)) || || || \\||  \\\\__ ||___";

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
