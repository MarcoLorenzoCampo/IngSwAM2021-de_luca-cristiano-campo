package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.actions.LorenzoAction;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.token.IToken;
import it.polimi.ingsw.network.views.IView;
import it.polimi.ingsw.network.eventHandlers.ViewObservable;
import it.polimi.ingsw.network.utilities.NetworkInfoValidator;
import it.polimi.ingsw.network.utilities.CommandParser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * This class offers a visual Interface via terminal. It is an implementation of the IView interface.
 * {@link IView}.
 */
public class CLI extends ViewObservable implements IView {

    /**
     * Clears the terminal.
     */
    public void clearCLI() {
        out.print(ColorCLI.CLEAR);
        out.flush();
    }

    private String nickname;
    private final PrintStream out;

    public CLI() {
        this.out = new PrintStream(System.out, true);
    }

    public void startCli() {
        printLogo();
        try {
            askLocal();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks the player if the game will be performed in local or online.
     */
    private void askLocal() throws ExecutionException {
        String choice;

        out.println("\nWant to play an online game or a local single player game? \n[OFF = offline, ON = online]");
        out.print(">>> ");

        while(true) {
            choice = readLine();

            if(choice.equalsIgnoreCase("off")) {

                //methods to play offline
                break;
            } else if(choice.equalsIgnoreCase("on")) {

                askServerInformation();
                break;

            } else {
                out.println("\nInvalid input! [OFF = offline, ON = online]");
                out.print(">>> ");
            }
        }
    }

    /**
     * Asks the player to specify a port and Ip address to connect to. They will be then validated and used.
     */
    private void askServerInformation() throws ExecutionException {
        out.println("\nYou've selected: online game." +
                "\nWe will now need a few parameters to set up the socket connection. ");

        String IPAddress = null;
        int port = -1;

        do {

            out.println("\nSpecify a valid socket port: " + "\nDefault one is 2200");
            out.print(">>> ");

            try {
                port = Integer.parseInt(readLine());
            } catch (NumberFormatException | InputMismatchException e) {
                out.println("This in not number!");
                clearCLI();
            }

        } while(!NetworkInfoValidator.isPortValid(port));

        do {

            out.println("\nSpecify a valid IPAddress: " + "\nDefault is 0.0.0.0");
            out.print(">>> ");
            
            try {
                IPAddress = readLine();

            } catch (InputMismatchException e) {
                out.println("Invalid format!");
            }
        } while(!NetworkInfoValidator.isIPAddressValid(IPAddress));

        String finalIPAddress = IPAddress;
        int finalPort = port;

        notifyObserver(o -> o.onServerInfoUpdate(finalPort, finalIPAddress));
    }

    private void printLogo() {
        out.println(Logo.getLogo() + Logo.getGreetings());
    }

    @Override
    public void askNickname() {

        out.println("\nWhat nickname would you like to use? ");
        out.print(">>> ");

        try {
            this.nickname = readLine();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        notifyObserver(o -> o.onUpdateNickname(nickname));
    }

    @Override
    public void askPlayerNumber() {
        out.println("\n\nHow many people are going to play?" +
                "\n[1 = Online Single Player Match, 4 = Max Players Allowed]");

        int lobbySize = 0;

        while(true) {
            try {
                out.print(">>> ");
                lobbySize = Integer.parseInt(readLine());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

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
            out.println("\nThe connection was successful, however your nickname was rejected.\nChose a new one.");
            askNickname();

        } else if(reconnected) {
            out.println("\nYou've been recognized and reconnected to the game.");

        } else {

            out.println("\nWe had trouble contacting the server. Game can't be played.");
            out.println(Logo.getDisgracefulEnding());

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

        ResourceType picked = null;

        do {
            out.println("Specify a valid resource: " + r1 + ", " + r2 + " are available.");
            out.print(">>> ");

            try {
                picked = ResourceType.valueOf(readLine());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        } while (picked == null || (!picked.equals(r1) && !picked.equals(r2)));

        ResourceType finalPicked = picked;
        notifyObserver(o -> o.onUpdateExchangeResource(finalPicked));
    }

    @Override
    public void askToDiscard() {

        out.println("You have 4 leader cards, you need to discard one of them" +
                "\nSpecify the two indexes of the cards you want to discard. Chose wisely!" +
                "(cards go from 0 to 3)");

        int d1 = -1;
        int d2 = -1;

        while(true) {
            out.println("\nFirst Card: ");
            out.print(">>> ");

            try {
                d1 = Integer.parseInt(readLine());
            } catch (NumberFormatException | ExecutionException e) {
                out.println("This is not a number!");
            }

            if(d1 <= 3 && d1 >= 0) {
                out.println("First leader card has been discarded!");
                break;
            }
        }

        while(true) {
            out.println("\nSecond Card: ");
            out.print(">>> ");

            try {
                d2 = Integer.parseInt(readLine());
            } catch (NumberFormatException | ExecutionException e) {
                out.println("This is not a number!");
            }

            if(d1 != d2) {
                if (d2 <= 3 && d2 >= 0) {
                    out.println("Second leader card has been discarded!");
                    break;
                }
            }
        }

        int finalD1 = d1;
        int finalD2 = d2;
        notifyObserver(o -> o.onUpdateSetupLeaders(finalD1, finalD2));
    }

    @Override
    public void showError(String errorMessage) {

    }

    /**
     * When the current player's turn is on, he's able to communicate once again.
     * @param message: generic message to alert the player his turn has started.
     */
    @Override
    public void currentTurn(String message) {
        showGenericString(message);

        boolean yourTurn = true;
        String cmd = "";

        //enable player input
        out.println("\nIt's your turn now. Chose an action to perform!" +
                "\n[type -help for a list the complete list of actions]");

        while(yourTurn) {

            out.print(">>> ");

            try {
                cmd = readLine();
            } catch (ExecutionException e) {
                out.println("\nCouldn't get this input.");
            }

            String[] cmdMembers = cmd.split(" ");

            switch(CommandParser.parseCmd(cmdMembers)) {

                case ("HELP") :
                    printPossibleActions();
                    break;

                case ("DISCARD_LEADER") :
                    notifyObserver(o -> o.onUpdateDiscardLeader(Integer.parseInt(cmdMembers[1])));
                    break;
            }
        }
    }

    /**
     * When the current player's turn is over, his input stream is shut down.
     * @param message: generic message.
     */
    @Override
    public void turnEnded(String message) {
        showGenericString(message);

        //disable player input

    }

    @Override
    public void askSetupResource(int number) throws ExecutionException {

        List< ResourceType> finalPicked = new ArrayList<>();
        ResourceType picked;
        if(number == 0){
            out.println("\nYou have 0 resources to pick, chose wisely!");

        }else {
            out.println("\nYou have " + number + " resource to pick, chose wisely!");
        }

        while(number > 0) {
            out.println("Available Resources: [SHIELD], [STONE], [SERVANT], [COIN]");
            picked = ResourceType.valueOf(readLine());

            if(picked.equals(ResourceType.SERVANT) || picked.equals(ResourceType.COIN)
            || picked.equals(ResourceType.STONE) || picked.equals(ResourceType.SHIELD)) {

                finalPicked.add(picked);
                out.println("Resource accepted! Added to your inventory.");
            } else {
                out.println("Invalid resource!");
                out.println("AVAILABLE RESOURCES: SHIELD, STONE, SERVANT, COIN");
            }
            number--;
        }
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
    public void printResourceMarket(ResourceType[][] resourceMarket, ResourceType extraMarble) {

    }

    @Override
    public void printAvailableCards(List<ProductionCard> availableCards) {

    }

    @Override
    public void printFaithTrack(FaithTrack faithTrack) {

    }

    @Override
    public void printLorenzoToken(IToken lorenzoAction) {

    }

    /**
     * Print every possible action allowed during the playing phase.
     */
    private void printPossibleActions() {

        out.println(
            "-------------------------------------------------------------------------------------------------------------" +
            "\nHere's a complete list of the accepted commands:" +
                "\n - 'DISCARD_LEADER': Discards one of your leader cards (Requires a valid card index);" +
                "\n - 'ACTIVATE_LEADER': Places one of your leader cards (Requires a valid card index);" +
                "\n - 'GET_RESOURCES': Gets resources from the market (Requires and index form 0 to 6);" +
                "\n - 'BUY_CARD': Buys an available card (Requires a valid card index and a valid production slot index);" +
                "\n - 'BASE_PRODUCTION':Activates the base production (asks you 2 input resources and 1 output resource);" +
                "\n - 'CARD_PRODUCTION': " +
                "\n - 'PEEK_<enemy nickname>': Checks on one of your enemies;" +
                "-------------------------------------------------------------------------------------------------------------"
        );
    }

    /**
     * Reads a line from standard input.
     *
     * @return the string read from the input.
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    public String readLine() throws ExecutionException {

        FutureTask<String> futureTask = new FutureTask<>(new InputReadingChore());
        Thread inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }
}