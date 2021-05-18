package it.polimi.ingsw.controller;

import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.player.PlayerState;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.builders.LeaderCardsDeckBuilder;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.views.cli.ColorCLI;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.network.server.Server.LOGGER;

/**
 * Class to manage players and turns in a multiplayer game.
 */
public final class MultiPlayerLobbyManager implements ILobbyManager {

    /**
     * Hardcoded. Default value and can't be changed.
     */
    private static final int MAX_PLAYERS = 4;

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
    private int numberOfTurns, auxIndex;

    /**
     * List of all player, both connected and disconnected.
     */
    private final List<RealPlayer> realPlayerList;

    /**
     * Map to store tuples: (Nickname, VirtualView).
     */
    private final Map<String, VirtualView> viewsByNickname;

    public MultiPlayerLobbyManager(GameManager gameManager) {
        this.realPlayerList = new LinkedList<>();
        numberOfTurns = 0;
        auxIndex = 0;
        this.gameManager = gameManager;
        viewsByNickname= new HashMap<>();
    }

    /**
     * Adds a new player if the validation steps are verified.
     * @param nickname: The name chosen by the player.
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

        //sets the new logged player as connected.
        realPlayerList
                .stream()
                .filter(p -> p.getName().equals(nickname))
                .forEach(p -> p.getPlayerState().connect());
    }

    /**
     * Sets the boolean value of the connection to true.
     * @param nickname: name associated to the player;
     */
    @Override
    public void reconnectPlayer(String nickname) {

        for(RealPlayer realPlayer : realPlayerList) {
            if(realPlayer.getName().equals(nickname)) {
                realPlayer.getPlayerState().connect();

                viewsByNickname
                        .get(nickname)
                        .showLoginOutput(true, true, true);

                broadcastToAllExceptCurrent("Player reconnected: " + nickname, nickname);
            }
        }
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
        viewsByNickname.get(realPlayerList.get(auxIndex).getName())
                .turnEnded("Your turn has ended.");

        numberOfTurns++;
        auxIndex ++;

        int newCurrentIndex = auxIndex % realPlayerList.size();

        if (!realPlayerList.get(newCurrentIndex).getPlayerState().isConnected()) {
            while (realPlayerList.get(newCurrentIndex).getPlayerState().isConnected()) {
                if (newCurrentIndex == realPlayerList.size() - 1) {
                    newCurrentIndex = 0;
                }
            }
        }

        auxIndex = newCurrentIndex;

        String nowPlaying = realPlayerList.get(newCurrentIndex).getName();

        gameManager.getCurrentGame()
                .setCurrentPlayer(realPlayerList.get(newCurrentIndex));

        gameManager.setCurrentPlayer(nowPlaying);

        //viewsByNickname.get(nowPlaying).currentTurn("It's your turn now");

        broadcastToAllExceptCurrent("Now playing: " + nowPlaying, nowPlaying);
        gameManager.onStartTurn();
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

                    realPlayerList.get(i)
                            .getPlayerBoard()
                            .getFaithTrack()
                            .increaseFaithMarker();
                    break;

                case 3:

                    viewsByNickname.get(realPlayerList.get(i).getName()).askSetupResource(2);

                    for(int j=0; j<2; j++) {
                        realPlayerList.get(i)
                                .getPlayerBoard()
                                .getFaithTrack()
                                .increaseFaithMarker();
                    }
                    break;
            }
    }


    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    @Override
    public int getLobbySize() {
        return lobbySize;
    }

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
    public void broadCastMatchInfo() {
        for(String playingName : getPlayingNames()) {

            viewsByNickname.get(playingName).showMatchInfo(getPlayingNames());
        }
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

            //If the game is started, then he needs to be set as "disconnected".
            if (gameStarted) {

                realPlayerList
                        .stream()
                        .filter(p -> p.getName().equals(nicknameToDisconnect))
                        .forEach(p -> p.getPlayerState().disconnect());

                //If he was playing when he got disconnected, then his turn needs to be skipped.
                if(nicknameToDisconnect.equals(gameManager.getCurrentGame().getCurrentPlayer().getName())) {
                    setNextTurn();
                    LOGGER.info("Removed " + nicknameToDisconnect + " from the game.");

                    gameManager.getLobbyManager().broadcastGenericMessage(nicknameToDisconnect
                            + " was removed from the game.");
                }

                if(realPlayerList.stream().filter(p -> p.getPlayerState().isConnected()).count() == 1) {

                    String lastOnline = realPlayerList
                            .stream()
                            .filter(p -> p.getPlayerState().isConnected())
                            .collect(Collectors.toList()).get(0).getName();

                    broadCastWinMessage(lastOnline);
                }
            }

            //If the game wasn't started yet, then he will be removed completely from the game.
            if (!gameStarted) {

                realPlayerList.removeIf(realPlayer -> realPlayer.getName().equals(nicknameToDisconnect));
                viewsByNickname.remove(nicknameToDisconnect);
                broadcastGenericMessage(nicknameToDisconnect + " was removed from the game.");
                LOGGER.info("Removed " + nicknameToDisconnect + " from setup phase.");
                broadcastGenericMessage("["+ (lobbySize-realPlayerList.size()) + " players left]");
            }

        } else {
            LOGGER.info(() -> "Removed a client before the login phase.");
        }
    }
}
