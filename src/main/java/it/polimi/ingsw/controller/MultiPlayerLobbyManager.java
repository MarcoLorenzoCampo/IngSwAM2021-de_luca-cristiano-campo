package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;
import it.polimi.ingsw.network.eventHandlers.Observer;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.serverMessages.VaticanReportNotification;
import it.polimi.ingsw.network.server.Server;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.network.server.Server.LOGGER;

/**
 * Class to manage players and turns in a multiplayer game.
 */
public class MultiPlayerLobbyManager implements Observer, ILobbyManager {

    /**
     * Lobby dimension set by the first client to connect.
     */
    private int lobbySize;

    /**
     * reference to the game manager.
     */
    private final GameManager gameManager;

    /**
     * Integer to track the progression of the turns and keep track of the playing order.
     * auxIndex is used to store temporary positions in the player list.
     */
    private int auxIndex;

    /**
     * List of all player, both connected and disconnected.
     */
    private final List<RealPlayer> realPlayerList;

    private boolean endGame;

    /**
     * Map to store tuples: (Nickname, VirtualView).
     */
    private final Map<String, VirtualView> viewsByNickname;

    public MultiPlayerLobbyManager(GameManager gameManager) {
        this.realPlayerList = new LinkedList<>();
        auxIndex = 0;
        this.gameManager = gameManager;
        viewsByNickname= new HashMap<>();
        endGame = false;
    }

    /**
     * Adds a new player if the validation steps are verified.
     * @param nickname: The name chosen by the player
     * @param virtualView: virtual view associated with the player.
     */
    @Override
    public void addNewPlayer(String nickname, VirtualView virtualView) {

        if(viewsByNickname.isEmpty()) {

            viewsByNickname.put(nickname, virtualView);
            realPlayerList.add(new RealPlayer(nickname));

            virtualView.showGenericString("\nYou're connected, waiting for the lobby to fill." +
                    " ["+ (lobbySize-realPlayerList.size()) + " players left]");
        }

        //from the second player on, he gets registered in this branch.
        else if(viewsByNickname.size() < lobbySize) {

            viewsByNickname.put(nickname, virtualView);
            realPlayerList.add(new RealPlayer(nickname));

            broadcastToAllExceptCurrent("New player added: " + nickname, nickname);
            broadcastGenericMessage(" ["+ (lobbySize-realPlayerList.size()) + " players left]");
        }

        connectPlayer(nickname);
        setObserver(nickname, virtualView);
    }

    /**
     * Sets the boolean value of the connection to true.
     * @param nickname: name associated to the player;
     */
    @Override
    public void reconnectPlayer(String nickname, VirtualView vv) {

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerState().connect();
        viewsByNickname.put(nickname, vv);
        gameManager.getVirtualViewLog().put(nickname, vv);

        vv.showLoginOutput(true, true, true);
        broadcastGenericMessage("Player reconnected: " + nickname);

        reconnectObserver(nickname, vv);
        sendReducedModel(nickname, vv);
    }

    /**
     * Method invoked when a lobby size message is sent.
     * @param lobbySize: number of player accepted.
     */
    public void setLobbySize(int lobbySize) {
        this.lobbySize = lobbySize;
    }

    /**
     * Method to set the playing order once the game starts. It's invoked once per game.
     */
    @Override
    public void setPlayingOrder() {

        Collections.shuffle(realPlayerList);
        realPlayerList.get(0).setFirstToPlay();
        PlayingGame.getGameInstance().setCurrentPlayer(realPlayerList.get(0));

        broadcastGenericMessage("Playing order has been set! Here's the current order:\n" +
                getPlayingNames());

        gameManager.setCurrentPlayer(realPlayerList.get(0).getName());
        viewsByNickname.get(realPlayerList.get(0).getName()).showGenericString("\nYou're the first player!");

        giveLeaderCards();

        //Showing the players the starting game condition.
        showStartingUpdates();

        gameManager.onStartTurn();
        //setDefaultResources();
    }

    /**
     * Iterates over the handler's list in the server, looking for the next player. The iteration goes on
     * as long as it finds a connected host to set as the next current player.
     *
     * Clients aren't removed from the player list since they might reconnect later. No progress they made
     * will be lost this way.
     *
     * The current player is alerted his turn is over, and so are the other players.
     */
    @Override
    public void setNextTurn() {

        auxIndex++;

        int newCurrentIndex = auxIndex % realPlayerList.size();

        if(endGame && (newCurrentIndex == realPlayerList.size()-1)) {
            HashMap<RealPlayer, Integer> victoryPoints = new HashMap<>();

            for (RealPlayer iterator: realPlayerList) {
                if(iterator.getPlayerState().isConnected())
                victoryPoints.put(iterator, iterator.computeTotalVictoryPoints());
            }

            Map.Entry<RealPlayer, Integer> maxEntry = null;

            for (Map.Entry<RealPlayer, Integer> entry : victoryPoints.entrySet()) {
                if (maxEntry == null || entry.getValue()
                        .compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }

            assert maxEntry != null;
            broadCastWinMessage("Game ended! " + maxEntry.getKey().getName() + " won!" +
                    "\nHe scored: " + maxEntry.getValue() + " points!");

        } else {

            if (!realPlayerList.get(newCurrentIndex).getPlayerState().isConnected()) {
                while (!realPlayerList.get(newCurrentIndex).getPlayerState().isConnected()) {
                    newCurrentIndex++;
                    if (newCurrentIndex == realPlayerList.size()) {
                        newCurrentIndex = 0;
                    }
                }
            }

            auxIndex = newCurrentIndex;

            String nowPlaying = realPlayerList.get(newCurrentIndex).getName();

            gameManager.getCurrentGame()
                    .setCurrentPlayer(realPlayerList.get(newCurrentIndex));

            gameManager.setCurrentPlayer(nowPlaying);

            forwardPlayerUpdates();

            broadcastToAllExceptCurrent("Now playing: " + nowPlaying, nowPlaying);
            gameManager.onStartTurn();
        }
    }

    /**
     * Method to deal 4 leader cards {@link LeaderCard} for each player.
     */
    @Override
    public void giveLeaderCards() {
        List<LeaderCard> leaderCards = LeaderCardsDeckBuilder.deckBuilder();

        for(int i=0; i<realPlayerList.size(); i++) {
            realPlayerList
                    .get(i)
                    .setOwnedLeaderCards(new ArrayList<>(leaderCards.subList(4 * i, 4 * i + 4)));
        }

        broadcastGenericMessage("Leader cards have been dealt.");
    }

    /**
     * When the game starts, each player is given a specific number of resources and
     * faith track points.
     */
    public void setDefaultResources(String current) {

            int i = turnOfPlayer(current);

            switch(i) {
                case 0:
                    viewsByNickname.get(current).askSetupResource(0);
                    break;

                case 1:
                    viewsByNickname.get(current).askSetupResource(1);
                    break;

                case 2:
                    viewsByNickname.get(realPlayerList.get(i).getName()).askSetupResource(1);
                    realPlayerList.get(i).moveFaith();
                    break;

                case 3:
                    viewsByNickname.get(realPlayerList.get(i).getName()).askSetupResource(2);
                    for(int j=0; j<2; j++) {
                        realPlayerList.get(i).moveFaith();
                    }
                    break;
            }
    }

    @Override
    public int getLobbySize() {
        return lobbySize;
    }

    @Override
    public List<RealPlayer> getRealPlayerList() {
        return realPlayerList;
    }

    /**
     * Method to send a generic message to all the clients except for the current one.
     * @param message: String to send.
     * @param nicknameToExclude: current player.
     */
    public void broadcastToAllExceptCurrent(String message, String nicknameToExclude) {

        viewsByNickname.entrySet().stream()
                .filter(entry -> !nicknameToExclude.equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .forEach(vv -> vv.showGenericString(message));
    }

    /**
     * Broadcasts a generic string to every client connected.
     * @param message: string to show.
     */
    public void broadcastGenericMessage(String message) {
        viewsByNickname.values()
                .forEach(vv -> vv.showGenericString(message));
    }

    @Override
    public void broadCastWinMessage(String winner) {
        viewsByNickname.values()
                .forEach(vv -> vv.showWinMatch(winner));

        gameManager.resetFSM();
    }

    @Override
    public int turnOfPlayer(String current) {
        int i = 0;
        while (!realPlayerList.get(i).getName().equals(current)){
            i++;
        }
        return i;
    }

    /**
     * Method to return a list of all the current clients connected.
     * @return: a list of all the connected user names.
     */
    public List<String> getPlayingNames() {

        return realPlayerList.stream()
                .filter(player -> player.getPlayerState().isConnected())
                .map(RealPlayer::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void disconnectPlayer(String nicknameToDisconnect) {

        boolean gameStarted = gameManager.isGameStarted();

        //If the nickname is null, that means the player's setup wasn't done.
        if(nicknameToDisconnect != null) {

            RealPlayer playerToDisconnect = realPlayerList.get(getPlayerIndexByNickname(nicknameToDisconnect));

            //If the game is started, then he needs to be set as "disconnected".
            if (gameStarted) {
                gameStartedDisconnection(playerToDisconnect, nicknameToDisconnect);
            }

            //If the game wasn't started yet, then he will be removed completely from the game.
            if (!gameStarted) {
                gameNotStartedDisconnection(nicknameToDisconnect);
            }
        } else {
            LOGGER.info(() -> "Removed a client before the login phase.");
        }
    }

    /**
     * Auxiliary method to set a specific player as "connected".
     */
    private void connectPlayer(String nickname) {

        //sets the new logged player as connected.
        realPlayerList
                .stream()
                .filter(p -> p.getName().equals(nickname))
                .forEach(p -> p.getPlayerState().connect());
    }

    /**
     * Auxiliary method to make player's virtual views observe directly the model.
     * @param virtualView: new observer.
     */
    @Override
    public void setObserver(String nickname, VirtualView virtualView) {

        realPlayerList.get(getPlayerIndexByNickname(nickname)).addObserver(this);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getFaithTrack().addObserver(this);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getInventoryManager().addObserver(this);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().addObserver(this);

        gameManager.getCurrentGame().getGameBoard().getResourceMarket().addObserver(virtualView);

        gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().addObserver(virtualView);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getInventoryManager().addObserver(virtualView);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getFaithTrack().addObserver(virtualView);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getProductionBoard().addObserver(virtualView);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).addObserver(virtualView);


    }

    /**
     * Auxiliary method to set a new observer to the player after disconnecting the previous one.
     * @param nickname: name to reconnect.
     * @param virtualView: virtual view to re-observer the player.
     */
    private void reconnectObserver(String nickname, VirtualView virtualView) {

        gameManager.getCurrentGame().getGameBoard().getResourceMarket().addObserver(virtualView);

        gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().addObserver(virtualView);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getInventoryManager().addObserver(virtualView);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getFaithTrack().addObserver(virtualView);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getProductionBoard().addObserver(virtualView);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).addObserver(virtualView);
    }

    /**
     * Method to send updates to the player when the game starts.
     */
    @Override
    public void showStartingUpdates() {
        for (Map.Entry<String, VirtualView> entry : viewsByNickname.entrySet()) {

            entry.getValue().printResourceMarket(PlayingGame.getGameInstance().getGameBoard().getResourceMarket().getResourceBoard(),
                    PlayingGame.getGameInstance().getGameBoard().getResourceMarket().getExtraMarble());
            entry.getValue().printAvailableCards(PlayingGame.getGameInstance().getGameBoard().getProductionCardMarket().getAvailableCards());

            entry.getValue().printFaithTrack(PlayingGame.getGameInstance().getCurrentPlayer().getPlayerBoard().getFaithTrack());
        }
    }

    /**
     * Helper method to find a player in the player list by using his nickname.
     * @param nickname: name to find
     * @return: index of said nickname.
     */
    private int getPlayerIndexByNickname(String nickname) {

        for(int i=0; i<realPlayerList.size(); i++) {
            if(realPlayerList.get(i).getName().equals(nickname)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * The lobby manager observes each player's faith track and warehouse, registering events.
     * @param message: controller notification.
     */
    @Override
    public void update(Message message) {

        switch(message.getMessageType()) {
            case VATICAN_REPORT_NOTIFICATION:

                VaticanReportNotification v = (VaticanReportNotification) message;

                int popeTileIndex = v.getPopeTileIndex();
                int rangeToCheck = v.getRange();

                for(RealPlayer realPlayer : realPlayerList) {

                    if(realPlayer.getPlayerState().isConnected() && !realPlayer.getName().equals(gameManager.getCurrentPlayer())) {
                        FaithTrack ft = realPlayer.getPlayerBoard().getFaithTrack();

                        //If the players' position is before a vatican section, then he won't get the points.
                        if (ft.getFaithMarker() < (popeTileIndex - rangeToCheck)) {
                            ft.setPopeTileInactive(popeTileIndex);
                        }
                    }
                }
                break;

            case DISCARDED_RESOURCE:
                broadcastGenericMessage(gameManager.getCurrentPlayer() + " discarded a resource, everyone moves!");
                for(RealPlayer realPlayer : realPlayerList) {
                    if(realPlayer.getPlayerState().isConnected()
                        && !realPlayer.getName().equals(gameManager.getCurrentPlayer())) {
                        realPlayer.moveFaith();
                    }
                }
                break;

            case END_GAME:
                if(!endGame) {
                    Server.LOGGER.info("Last round started.");
                    broadcastGenericMessage("End game started by: " + gameManager.getCurrentPlayer());
                    endGame = true;
                }
                break;

            case BOUGHT_7_CARDS:
                if(!endGame) {
                    endGame = true;
                    Server.LOGGER.info("Last round started.");
                    broadcastGenericMessage("End game started by: " + gameManager.getCurrentPlayer());
                }
                break;

            default: //Ignore any other message
                break;
        }
    }

    /**
     * Method to send the stored data of a reconnected player.
     * @param nickname: player who get reconnected.
     * @param vv: virtual view of said player.
     */
    private void sendReducedModel(String nickname, VirtualView vv) {
        vv.printFaithTrack(realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getFaithTrack());

        vv.printAvailableCards(gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().getAvailableCards());

        vv.printResourceMarket(gameManager.getCurrentGame().getGameBoard().getResourceMarket().getResourceBoard(),
                gameManager.getCurrentGame().getGameBoard().getResourceMarket().getExtraMarble());

        vv.printLeaders(realPlayerList.get(getPlayerIndexByNickname(nickname)).getOwnedLeaderCards());

        //send buffer, strongbox, warehouse.

        vv.printBuffer(new ArrayList<>());

        //vv.printWarehouse(realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getInventoryManager().getWarehouse());

        vv.printStrongbox(realPlayerList.get(getPlayerIndexByNickname(nickname))
                .getPlayerBoard().getInventoryManager().getStrongbox().getInventory());
    }

    /**
     * When a player gets disconnected, he gets removed from the list of observers.
     * @param nickname: player who got disconnected.
     */
    private void removeObserver(String nickname) {

        VirtualView vv = viewsByNickname.get(nickname);

        gameManager.getCurrentGame().getGameBoard().getResourceMarket().removeObserver(vv);

        gameManager.getCurrentGame().getGameBoard().getProductionCardMarket().removeObserver(vv);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getInventoryManager().removeObserver(vv);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getFaithTrack().removeObserver(vv);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).getPlayerBoard().getProductionBoard().removeObserver(vv);

        realPlayerList.get(getPlayerIndexByNickname(nickname)).removeObserver(vv);
    }

    /**
     * When a player gets disconnected during the setup phase, he gets a randomized setup. This means he will
     * be given random resources according to his index in the playing list and discarded 2 random leader cards.
     * @param disconnectedNickname: player who got disconnected.
     */
    @Override
    public void randomizedResourcesSetup(String disconnectedNickname) {

        RealPlayer disconnected = realPlayerList.get(getPlayerIndexByNickname(disconnectedNickname));

        switch(getPlayerIndexByNickname(disconnectedNickname)) {

            case 1:
                disconnected.assignRandomResource();
                break;

            case 2:
                for(int i=0; i<2; i++) {
                    disconnected.assignRandomResource();
                }
                disconnected.moveFaith();
                break;

            case 3:
                for(int i=0; i<2; i++) {
                    disconnected.assignRandomResource();
                    disconnected.moveFaith();
                }
                break;

            default: break; //Should never happen.
        }
    }

    /**
     * Discards two random leader cards when a player gets disconnected during the setup phase.
     * @param disconnectedNickname: player who got disconnected.
     */
    @Override
    public void randomizedLeadersSetup(String disconnectedNickname) {

        RealPlayer disconnected = realPlayerList.get(getPlayerIndexByNickname(disconnectedNickname));

        int n1 = new Random().nextInt(3);
        int n2;

        do {
            n2 = new Random().nextInt(3);
        } while (n1 == n2);

        disconnected.setupLeaderCard(n1);
        disconnected.setupLeaderCard(n2);
    }

    /**
     * Method to forward each player an updated reduced version of each enemy to be accessed as local information.
     */
    private void forwardPlayerUpdates() {

        RealPlayer currentPlayer = realPlayerList.get(getPlayerIndexByNickname(gameManager.getCurrentPlayer()));

        if(currentPlayer.getPlayerState().isConnected()) {
            for(RealPlayer realPlayer1 : realPlayerList) {

                if(!realPlayer1.getName().equals(currentPlayer.getName())) {

                    viewsByNickname.get(currentPlayer.getName()).getPeek(
                            realPlayer1.getName(),
                            realPlayer1.getFaithPosition(),
                            realPlayer1.getInventory(),
                            realPlayer1.reduceLeaders()
                    );
                }
            }
        }
    }

    /**
     * Handles a disconnection when the game is already started.
     * @param playerToDisconnect: player to remove.
     * @param nicknameToDisconnect: nickname to remove.
     */
    private void gameStartedDisconnection(RealPlayer playerToDisconnect, String nicknameToDisconnect) {

        playerToDisconnect.getPlayerState().disconnect();

        removeObserver(nicknameToDisconnect);
        viewsByNickname.remove(nicknameToDisconnect);
        gameManager.getVirtualViewLog().remove(nicknameToDisconnect);

        //Fixes the fsm to end every action that is being made by the player who got disconnected.
        gameManager.prepareForNextTurn(playerToDisconnect);

        //If he was playing when he got disconnected, then his turn needs to be skipped.
        if(nicknameToDisconnect.equals(gameManager.getCurrentGame().getCurrentPlayer().getName())) {

            gameManager.getLobbyManager().broadcastGenericMessage(nicknameToDisconnect
                    + " was removed from the game.");

            LOGGER.info("Removed " + nicknameToDisconnect + " from the game.");

            setNextTurn();
        }

        if(realPlayerList.stream().filter(p -> p.getPlayerState().isConnected()).count() == 1) {

            String lastOnline = realPlayerList
                    .stream()
                    .filter(p -> p.getPlayerState().isConnected())
                    .collect(Collectors.toList()).get(0).getName();

            broadCastWinMessage(lastOnline);
        }

        if(realPlayerList.stream().noneMatch(p -> p.getPlayerState().isConnected())) {
            gameManager.endGame("No more players connected!");
        }
    }

    /**
     * Handles disconnection when the game isn't started yet.
     * @param nicknameToDisconnect: name to disconnect.
     */
    private void gameNotStartedDisconnection(String nicknameToDisconnect) {
        realPlayerList.removeIf(realPlayer -> realPlayer.getName().equals(nicknameToDisconnect));
        viewsByNickname.remove(nicknameToDisconnect);
        broadcastGenericMessage(nicknameToDisconnect + " was removed from the game.");
        LOGGER.info("Removed " + nicknameToDisconnect + " from setup phase.");
        broadcastGenericMessage("\n["+ (lobbySize-realPlayerList.size()) + " players left]");
    }
}
