package it.polimi.ingsw.network.views.cli;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.token.AbstractToken;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.network.eventHandlers.ViewObserver;
import it.polimi.ingsw.network.views.IView;
import it.polimi.ingsw.network.eventHandlers.ViewObservable;
import it.polimi.ingsw.network.utilities.NetworkInfoValidator;
import it.polimi.ingsw.network.utilities.CommandParser;
import it.polimi.ingsw.network.views.cli.graphical.GraphicalFaithTrack;
import it.polimi.ingsw.network.views.cli.graphical.GraphicalToken;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static it.polimi.ingsw.enumerations.ResourceType.*;

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
        clearCLI();

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
        clearCLI();

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
            out.println(UsefulStrings.getDisgracefulEnding());

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
        clearCLI();

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

        lightweightModel.setLeaderCards(cards);

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
        out.println("Error: " + errorMessage);

        System.exit(1);
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

            switch (output) {
                case ("HELP") : printPossibleActions(); break;
                case ("CHECK_MARKET") :  out.println(lightweightModel.getReducedResourceMarket()); break;
                case ("CHECK_CARDS") : out.println(lightweightModel.getReducedAvailableCards()); break;
                case ("CHECK_LEADERS") : printLeaders(lightweightModel.getLeaderCards()); break;
            }

        } while (output.equals("UNKNOWN_COMMAND") || output.equals("HELP") || output.equals("CHECK_MARKET")
                || output.equals("CHECK_CARDS") || output.equals("CHECK_LEADERS"));

        switch(CommandParser.parseCmd(cmdMembers)) {

            case("EXECUTE"):
                notifyObserver(ViewObserver::onUpdateExecuteProduction);
                break;

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
                notifyObserver(ViewObserver::onUpdateEndTurn);
                break;

            case("WAREHOUSE") :
                notifyObserver(ViewObserver::onUpdateSourceWarehouse);
                break;

            case("STRONGBOX") :
                notifyObserver(ViewObserver::onUpdateSourceStrongBox);
                break;

            case("UNKNOWN_COMMAND"):
                out.println("\nParsing error!");
                break;
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
            || picked.equals(STONE) || picked.equals(ResourceType.SHIELD))) {

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

        for (LeaderCard iterator: leaderCards) {

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
    public void printBuffer(ArrayList<ResourceType> buffer) {
        lightweightModel.setBuffer(buffer);
        out.println("Buffer:");
        for (ResourceType iterator : buffer) {
            out.print(iterator + " ");
        }
        out.println();
    }

    @Override
    public void printStrongbox(HashMap<ResourceType, Integer> strongbox) {
        lightweightModel.setStrongbox(strongbox);
        out.println("STRONGBOX: " +"\nSHIELD = " + strongbox.get(ResourceType.SHIELD) +
                "\nCOIN = " + strongbox.get(ResourceType.COIN) +
                "\nSTONE = " + strongbox.get(STONE) +
                "\nSERVANT = " + strongbox.get(ResourceType.SERVANT));
        out.println();
    }

    @Override
    public void printWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras) {
        lightweightModel.setWarehouse(shelves, extras);
        out.println("extra shelves: " + extras);
        out.println("Warehouse: ");
        if(shelves.size() == 6) {
            out.println("SHELF 1 = " + shelves.get(0));
            out.println("SHELF 2 = " + shelves.get(1) + " " + shelves.get(2));
            out.println("SHELF 3 = " + shelves.get(3) + " " + shelves.get(4) + " " + shelves.get(5));
        }
        out.println();
        if(shelves.size()>6){
            for (int i = 6; i < shelves.size(); i++) {
                out.print(shelves.get(i) + " ");
            }
        }
    }

    @Override
    public void printProductionBoard(String productions) {
        out.println(productions);
    }

    @Override
    public void printFinalProduction(HashMap<ResourceType, Integer> input, HashMap<ResourceType, Integer> output) {
        out.println("INPUT: ");
        for (Map.Entry<ResourceType,Integer> iterator: input.entrySet()) {
            out.println(iterator);
        }
        out.println("OUTPUT: ");
        for (Map.Entry<ResourceType,Integer> iterator: output.entrySet()) {
            out.println(iterator);
        }
        out.println();
    }

    @Override
    public void printResourceMarket(ResourceType[][] resourceBoard, ResourceType extraMarble) {

        String reducedExtraMarble = null;
        switch (extraMarble) {
            case STONE:
                reducedExtraMarble = ColorCLI.ANSI_WHITE.escape() + "STONE  " + ColorCLI.ANSI_BRIGHT_WHITE.escape(); break;
            case SERVANT:
                reducedExtraMarble = ColorCLI.ANSI_PURPLE.escape() + "SERVANT" + ColorCLI.ANSI_WHITE.escape(); break;
            case COIN:
                reducedExtraMarble = ColorCLI.ANSI_YELLOW.escape() + "COIN   " + ColorCLI.ANSI_WHITE.escape(); break;
            case SHIELD:
                reducedExtraMarble = ColorCLI.ANSI_BLUE.escape() + "SHIELD " + ColorCLI.ANSI_WHITE.escape(); break;
            case UNDEFINED:
                reducedExtraMarble = "WHITE  "; break;
            case FAITH:
                reducedExtraMarble = ColorCLI.ANSI_RED.escape() + "FAITH  " + ColorCLI.ANSI_WHITE.escape(); break;
            default: break;
        }
        String reducedMarketBoard = "";

        String temp = "";
        int k=0;

        StringBuilder reducedMarketBoardBuilder = new StringBuilder(reducedMarketBoard
                + ("     0         1         2         3    "));
        reducedMarketBoardBuilder.append("\n╔═════════╦═════════╦═════════╦═════════╗\n");
        for(int i = 0; i<3; i++) {
            for(int j=0; j<4; j++) {

                switch (resourceBoard[i][j]) {
                    case STONE:
                        temp = ColorCLI.ANSI_WHITE.escape() + "STONE   " + ColorCLI.ANSI_BRIGHT_WHITE.escape(); break;
                    case SERVANT:
                        temp = ColorCLI.ANSI_PURPLE.escape() + "SERVANT " + ColorCLI.ANSI_BRIGHT_WHITE.escape(); break;
                    case COIN:
                        temp = ColorCLI.ANSI_YELLOW.escape() + "COIN    " + ColorCLI.ANSI_BRIGHT_WHITE.escape(); break;
                    case SHIELD:
                        temp = ColorCLI.ANSI_BLUE.escape() + "SHIELD  " + ColorCLI.ANSI_BRIGHT_WHITE.escape(); break;
                    case UNDEFINED:
                        temp = "WHITE   "; break;
                    case FAITH:
                        temp = ColorCLI.ANSI_RED.escape() + "FAITH   " + ColorCLI.ANSI_WHITE.escape(); break;
                    default: break;
                }

                k=j+1;
                reducedMarketBoardBuilder.append("║ ").append(temp);
            }
            reducedMarketBoardBuilder.append("║").append("  ").append(i+k);

            if(!(i == 2)) {
                reducedMarketBoardBuilder.append("\n╠═════════╣═════════╣═════════╣═════════╣\n");
            } else {
                reducedMarketBoardBuilder.append("\n╚═════════╩═════════╩═════════╩═════════╝");
            }
        }
        reducedMarketBoard = reducedMarketBoardBuilder.toString();

        reducedMarketBoard = reducedMarketBoard.concat("\nExtra marble -> ").concat(reducedExtraMarble).concat("\n");


        out.println("\nThe ResourceMarket has been modified, here's an updated version: \n");

        lightweightModel.setReducedResourceMarket(reducedMarketBoard);
        out.println(reducedMarketBoard);
    }

    @Override
    public void printAvailableCards(String reducedAvailableCards) {

        out.println("\nThe ProductionCardsMarket has been modified, here's an updated version: \n");

        lightweightModel.setReducedAvailableCards(reducedAvailableCards);
        out.println(reducedAvailableCards);
    }

    @Override
    public void printFaithTrack(FaithTrack faithTrack) {
        GraphicalFaithTrack graphicalFaithTrack = new GraphicalFaithTrack(faithTrack);
        graphicalFaithTrack.draw();
        out.println();
    }

    @Override
    public void printLorenzoToken(AbstractToken lorenzoAction) {

        out.println("\nLorenzo just played a token:\n");

        GraphicalToken graphicalToken = new GraphicalToken(lorenzoAction);
        graphicalToken.draw();
    }

    /**
     * Print every possible action allowed during the playing phase.
     */
    private void printPossibleActions() {
        out.println(UsefulStrings.getWhatToDo());
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
