package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.network.eventHandlers.IView;
import it.polimi.ingsw.network.eventHandlers.viewObservers.ViewObservable;
import it.polimi.ingsw.network.utilities.NetworkInfoValidator;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * This class offers a visual Interface via terminal. It is an implementation of the IView interface.
 * {@link it.polimi.ingsw.network.eventHandlers.IView}.
 */
public class CLI extends ViewObservable implements IView {

    private final PrintStream out;
    private Scanner in;

    public CLI() {
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out, true);
    }

    public void startCli() {
        printLogo();
        askLocal();
    }

    /**
     * Asks the player if the game will be performed in local or online.
     */
    private void askLocal() {
        String choice;

        out.println("\nWant to play an online game or a local single player game? \n[OFF = offline, ON = online]");

        while(true) {
            choice = in.nextLine();

            if(choice.equalsIgnoreCase("off")) {

                //methods to play offline
                break;
            } else if(choice.equalsIgnoreCase("on")) {

                askServerInformation();
                break;
            } else {
                out.println("\nInvalid input!! [OFF = offline, ON = online]");
            }
        }
    }

    /**
     * Asks the player to specify a port and Ip address to connect to. They will be then validated and used.
     */
    private void askServerInformation() {
        out.println("\nOkay now we will need a few parameters. ");

        String IPAddress = "";
        int port = -1;

        do {
            out.println("\nSpecify a valid port: ");

            try {
                port = in.nextInt();
            } catch (NumberFormatException e) {
                out.println("This in not number!");
                clearCLI();
            }
        } while(!NetworkInfoValidator.isPortValid(port));

        do {
            out.println("\nSpecify a valid IP address: ");

            try {
                IPAddress = in.nextLine();
            } catch (ClassFormatError e) {
                out.println("This in not String!");
                clearCLI();
            }
        } while(!NetworkInfoValidator.isIPAddressValid(IPAddress));

        String finalIPAddress = IPAddress;
        int finalPort = port;

        notifyObserver(o -> o.onServerInfoUpdate(finalPort, finalIPAddress));
    }

    private void printLogo() {
        out.println(Logo.getLogo());
        out.println("\nWelcome to Masters Of Renaissance!\nThis Version was implemented by:" +
                "\nMarco Lorenzo Campo, Alessandro De Luca, Mario Cristiano" +
                "\nHope you enjoy :) ");
    }

    @Override
    public void askNickname() {

        out.println("What nickname would you like to use? ");
        boolean isANickname = false;

        String nickname = in.nextLine();
        notifyObserver(o -> o.onUpdateNickname(nickname));
    }

    @Override
    public void askPlayerNumber() {
        out.println("\n\nHow many people are going to play? " +
                "[1 = Online Single Player Match\n4 = Max Players Allowed");

        int lobbySize;

        while(true) {
            lobbySize = in.nextInt();

            if(lobbySize < 1 || lobbySize > 4) {
                out.println("\nInvalid number of player!");
            } else {
                break;
            }
        }

        int finalLobbySize = lobbySize;
        notifyObserver(o -> o.onUpdateNumberOfPlayers(finalLobbySize));
    }

    @Override
    public void showLoginOutput(boolean connectionSuccess, boolean nicknameAccepted, boolean reconnected) {

        clearCLI();

        if(connectionSuccess && nicknameAccepted && !reconnected) {
            out.println("Connection successful! You're now logged in.");
        } else if(connectionSuccess && !nicknameAccepted && !reconnected) {
            out.println("The connection was successful, however your nickname was rejected.\nChose a new one.");
            askNickname();
        } else if(reconnected) {
            out.println("You've been recognized and reconnected to the game.");
        } else {
            out.println("We had trouble connecting to the server.");

            System.exit(1);
        }
    }

    @Override
    public void showGenericString(String genericMessage) {
        out.println(genericMessage);
    }

    @Override
    public void showInvalidAction(String errorMessage) {
        showGenericString(errorMessage);
    }

    @Override
    public void askReplacementResource(ResourceType r1, ResourceType r2) {
        out.println("You just bought resources from the market." +
                "It appears you have a choice though." +
                "\nWhat resource would you like to get for this white marble?");

        out.println(r1 + ", " + r2 + " are available.");


    }

    @Override
    public void askToDiscard() {

        out.println("You have 4 leader cards, you need to discard one of them" +
                "\nSpecify the two indexes of the cards you want to discard. Chose wisely!" +
                "(cards go from 0 to 3)");

        int d1 = -1;

        while(in.hasNext()) {

            out.println("\n\nFirst Card: ");
            try {
                d1 = in.nextInt();
            } catch (NumberFormatException e) {
                out.println("This is not a number!");
            }

            if(d1 <= 3 && d1 >= 0) {
                out.println("Leader cards have been discarded!");
                break;
            }
        }

        int finalD = d1;
        notifyObserver(o -> o.onUpdateDiscardLeader(finalD));
    }

    @Override
    public void showError(String errorMessage) {

    }

    /**
     * When the current player's turn is on, he's able to communicate once again.
     * @param message: generic message.
     */
    @Override
    public void currentTurn(String message) {
        showGenericString(message);

        in = new Scanner(System.in);
    }

    /**
     * When the current player's turn is over, his input stream is shut down.
     * @param message: generic message.
     */
    @Override
    public void turnEnded(String message) {
        showGenericString(message);

        in.close();
    }

    @Override
    public void askSetupResource() {
        out.println("\nYou have a resource to pick, chose wisely!");

        out.println("AVAILABLE RESOURCES: SHIELD, STONE, SERVANT, COIN");

        ResourceType picked = null;

        while(in.hasNext()) {

            picked = ResourceType.valueOf(in.nextLine());

            if(picked.equals(ResourceType.SERVANT) || picked.equals(ResourceType.COIN)
            || picked.equals(ResourceType.STONE) || picked.equals(ResourceType.SHIELD)) {

                out.println("Resource accepted! Added to your inventory.");
                break;
            } else {
                out.println("Invalid resource!");
                out.println("AVAILABLE RESOURCES: SHIELD, STONE, SERVANT, COIN");
            }
        }

        ResourceType finalPicked = picked;
        notifyObserver(o -> o.onUpdateSetupResource(finalPicked));
    }

    @Override
    public void showMatchInfo(List<String> playingNames) {

        out.println("These are the names of the active players:\n");

        for(String name : playingNames) {
            out.println(name + ", ");
        }
    }

    @Override
    public void showWinMatch(String winner) {
        out.println(winner + " won! Congratulations :)");

        System.exit(0);
    }

    @Override
    public void printResourceMarket(ResourceType[][] resourceMarket) {

    }

    @Override
    public void printAvailableCards(List<ProductionCard> availableCards) {

    }

    /**
     * Clears the CLI terminal.
     */
    public void clearCLI() {
        out.print(ColorCLI.CLEAR);
        out.flush();
    }
}
