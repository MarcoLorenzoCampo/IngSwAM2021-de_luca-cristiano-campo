package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.token.IToken;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.network.views.IView;
import it.polimi.ingsw.network.eventHandlers.ViewObservable;
import it.polimi.ingsw.network.utilities.NetworkInfoValidator;
import it.polimi.ingsw.network.utilities.CommandParser;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * This class offers a visual Interface via terminal. It is an implementation of the IView interface.
 * {@link IView}.
 */
public class CLI extends ViewObservable implements IView {

    /**
     * True if it's an offline game, False if it's an online game.
     */
    private final boolean isOffline;

    /**
     * Light and read only copy of the udated model.
     */
    private final LightweightModel lightweightModel;

    /**
     * Clears the terminal.
     */
    public static void clearCLI() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String nickname;
    private final PrintStream out;

    public CLI(boolean isOffline) {
        this.out = new PrintStream(System.out, true);
        this.isOffline = isOffline;
        this.lightweightModel = new LightweightModel();
    }

    public void startCli() {

        try {
            if(isOffline) {
                askNickname();
            } else {
                askServerInformation();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
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

        if(isOffline) {
            notifyObserver(o -> o.onUpdateNumberOfPlayers(1));

        } else {

            out.println("\n\nHow many people are going to play?" +
                    "\n[1 = Online Single Player Match, 4 = Max Players Allowed]");

            int lobbySize = 0;

            while (true) {
                try {
                    out.print(">>> ");
                    lobbySize = Integer.parseInt(readLine());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (lobbySize < 1 || lobbySize > 4) {
                    out.println("\nInvalid number of player!");
                } else {
                    break;
                }
            }

            int finalLobbySize = lobbySize;
            notifyObserver(o -> o.onUpdateNumberOfPlayers(finalLobbySize));
        }
    }

    @Override
    public void showLoginOutput(boolean connectionSuccess, boolean nicknameAccepted, boolean reconnected) {

        clearCLI();

        if(connectionSuccess && nicknameAccepted && !reconnected) {
            out.println("Operation successful! You're now logged in.");

        } else if(connectionSuccess && !nicknameAccepted && !reconnected) {
            out.println("\nYour nickname was rejected.\nChose a new one.");
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

        out.println("\nYou have 4 leader cards, you need to discard one of them" +
                "\nSpecify the two indexes of the cards you want to discard. Chose wisely!" +
                " (cards go from 0 to 3)");

        int d1 = -1, d2 = -1;

        while(true) {
            out.println("\nFirst Card: ");
            out.print(">>> ");

            try {
                d1 = Integer.parseInt(readLine());
            } catch (NumberFormatException | ExecutionException e) {
                out.println("This is not a number!\n");
            }

            if(d1 <= 3 && d1 >= 0) {
                out.println("First leader card has been discarded!\n");
                break;
            } else {
                out.println("This is not a valid number!\n");
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
                }else {
                    out.println("This is not a valid number!");
                }
            } else {
                out.println("You already discarded this card!");
            }
        }

        int finalD1 = d1, finalD2 = d2;
        notifyObserver(o -> o.onUpdateSetupLeaders(finalD1, finalD2));
    }

    @Override
    public void showLeaderCards(List<LeaderCard> cards) {


        for (LeaderCard iterator: cards) {

            switch (iterator.getEffectType()){
                case DISCOUNT:
                    out.println("\n\nDISCOUNT: -1 of "+ iterator.getResource() + "\nNeeded: ");
                    for (DevelopmentTag innerIterator : iterator.getRequirementsDevCards()) {
                        out.print("1 " + innerIterator.getColor() +"\n");
                    }
                    break;

                case EXTRA_INVENTORY:
                    out.println("\n\nEXTRA INVENTORY: + 2 spaces of "+ iterator.getResource() + "\nNeeded: ");
                    out.print("5 " + iterator.getRequirementsResource()[0].getType() +"\n");
                    break;

                case MARBLE_EXCHANGE:
                    out.println("\n\nMARBLE EXCHANGE: change white into "+ iterator.getResource() + "\nNeeded: ");
                    for (DevelopmentTag innerIterator : iterator.getRequirementsDevCards()) {
                        out.print(innerIterator.getQuantity() + " " + innerIterator.getColor() +" level: " + innerIterator.getLevel()+"\n");
                    }
                    break;
                case EXTRA_PRODUCTION:
                    out.println("\n\nEXTRA PRODUCTION:  "+ iterator.getResource() +" --> FAITH + UNDEFINED");
                    out.println("Needed: ");
                    for (DevelopmentTag innerIterator : iterator.getRequirementsDevCards()) {
                        out.print("1 " + innerIterator.getColor() +" level: " + innerIterator.getLevel()+"\n");
                    }
                    break;
            }
        }
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
        out.println(message);

        String cmd = "";
        String[] cmdMembers;
        String output;

        //enable player input
        out.println("\nIt's your turn now. Chose an action to perform!" +
                "\n[type -help for a complete list of actions]");

        do {
            out.print(">>> ");

            try {
                cmd = readLine();
            } catch (ExecutionException e) {
                out.println("\nCouldn't get this input.");
            }

            cmdMembers = cmd.split(" ");

            output = CommandParser.parseCmd(cmdMembers);

            if(output.equals("HELP")) printPossibleActions();

        } while (output.equals("UNKNOWN_COMMAND") || output.equals("HELP"));

        switch(CommandParser.parseCmd(cmdMembers)) {

            case ("DISCARD_LEADER") :
                String[] finalCmdMembers = cmdMembers;
                notifyObserver(o -> o.onUpdateDiscardLeader(Integer.parseInt(finalCmdMembers[1])));
                break;

            case ("ACTIVATE_LEADER") :
                String[] finalCmdMembers1 = cmdMembers;
                notifyObserver(o -> o.onUpdateActivateLeader(Integer.parseInt(finalCmdMembers1[1])));
                break;

            case("GET_RESOURCES") :
                String[] finalCmdMembers2 = cmdMembers;
                notifyObserver(o -> o.onUpdateGetResources(Integer.parseInt(finalCmdMembers2[1])));
                break;

            case("DEPOSIT_RESOURCE") :
                String[] finalCmdMembers3 = cmdMembers;
                notifyObserver(o -> o.onUpdateDeposit(Integer.parseInt(finalCmdMembers3[1])));
                break;

            case("ACTIVATE_PRODUCTION") :
                String[] finalCmdMembers4 = cmdMembers;
                notifyObserver(o -> o.onUpdateActivateProductionCard(Integer.parseInt(finalCmdMembers4[1])));
                break;

            case("BUY_CARD") :
                String[] finalCmdMembers5 = cmdMembers;
                notifyObserver(o -> o.onUpdateBuyCard(Integer.parseInt(finalCmdMembers5[1]), Integer.parseInt(finalCmdMembers5[2])));
                break;

            case("ACTIVATE_EXTRA_PRODUCTION") :
                String[] finalCmdMembers6 = cmdMembers;
                notifyObserver(o -> o.onUpdateActivateExtraProduction(Integer.parseInt(finalCmdMembers6[1]),ResourceType.valueOf(finalCmdMembers6[2])));
                break;

            case("ACTIVATE_BASE_PRODUCTION") :
                String[] finalCmdMembers7 = cmdMembers;
                notifyObserver(o -> o.onUpdateBaseActivation(ResourceType.valueOf(finalCmdMembers7[1]), ResourceType.valueOf(finalCmdMembers7[2]), ResourceType.valueOf(finalCmdMembers7[3])));
                break;

            case("END_TURN") :
                notifyObserver(o -> o.onUpdateEndTurn());
                break;

            case("WAREHOUSE") :
                notifyObserver(o -> o.onUpdateSourceWarehouse());
                break;

            case("STRONGBOX") :
                notifyObserver(o -> o.onUpdateSourceStrongBox());
                break;

            case("UNKNOWN_COMMAND"): break;
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

        LinkedList<ResourceType> finalPicked = new LinkedList<>();

        ResourceType picked = null;

        if(number == 0) {
            out.println("\nThe first player doesn't get to pick any resource!");
        } else {
            out.println("\nYou have " + number + " resource to pick, chose wisely!");
        }

        while(number > 0) {
            out.println("Available Resources: [SHIELD], [STONE], [SERVANT], [COIN] (All caps required)");
            out.print(">>> ");

            try {
                picked = ResourceType.valueOf(readLine());
            } catch (IllegalArgumentException e) {
                out.println("\nInvalid resource!");
            }

            if(picked != null && (picked.equals(ResourceType.SERVANT) || picked.equals(ResourceType.COIN)
            || picked.equals(ResourceType.STONE) || picked.equals(ResourceType.SHIELD))) {

                finalPicked.add(picked);
                out.println("Resource accepted! Added to your inventory.");

                number--;
            }
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
        out.println(winner + " won!");

        System.exit(0);
    }

    @Override
    public void printLeaders(List<LeaderCard> leaderCards) {

        //

        currentTurn("What's your action now?");
    }

    @Override
    public void printResourceMarket(String reducedResourceMarket) {

        out.println("\nThe ResourceMarket has been modified, here's an updated version: ");

        lightweightModel.setReducedResourceMarket(reducedResourceMarket);
        out.println(reducedResourceMarket);
    }

    @Override
    public void printAvailableCards(String reducedAvailableCards) {

        out.println("\nThe ProductionCardsMarket has been modified, here's an updated version: ");

        lightweightModel.setReducedAvailableCards(reducedAvailableCards);
        out.println(reducedAvailableCards);
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
                "\n - 'DISCARD_LEADER <int>': Discards one of your leader cards (Requires a valid card index);" +
                "\n - 'ACTIVATE_LEADER <int>': Places one of your leader cards (Requires a valid card index);" +
                "\n - 'GET_RESOURCES <int>': Gets resources from the market (Requires and index form 0 to 6);" +
                "\n - 'BUY_CARD <int>': Buys an available card (Requires a valid card index and a valid production slot index);" +
                "\n - 'BASE_PRODUCTION <ResourceType> <ResourceType> <ResourceType>': " +
                    "\n     Activates the base production (asks you 2 input resources and 1 output resource);" +
                "\n - 'CARD_PRODUCTION': " +
                "\n - 'PEEK_<enemy nickname>': Checks on one of your enemies;" +
                "\n--------------------------------------------------------------------------------------------------------------"
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
