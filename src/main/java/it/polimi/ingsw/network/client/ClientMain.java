package it.polimi.ingsw.network.client;

/**
 * Main method of the client class, it can be launched in both cli or gui mode.
 * The default option is gui, it can be used in cli mode by adding "-cli" when running the jar file.
 *
 * user @ user:$ client.jar -cli
 */
public class ClientMain {

    public static void main(String[] args) {

        boolean cli = false;

        for(String arg : args) {
            if (arg.equalsIgnoreCase("-cli")) {
                cli = true;
                break;
            }
        }

        if(cli) {
            //launch cli
        } else {
            //launch gui
        }
    }
}
