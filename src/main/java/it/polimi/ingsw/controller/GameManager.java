package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.DiscardLeaderCardAction;
import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.exceptions.DiscardResourceException;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.builders.ResourceBuilder;
import it.polimi.ingsw.network.eventHandlers.Observer;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.DiscardTwoLeaders;
import it.polimi.ingsw.network.messages.playerMessages.OneIntMessage;
import it.polimi.ingsw.network.messages.playerMessages.SetupResourceAnswer;
import it.polimi.ingsw.network.server.Server;

import java.io.Serializable;
import java.util.*;

/**
 * Class to manage the entire playing game. It has instances of the currentGame, LobbyManager,
 * ActionManager.
 */
public final class GameManager implements Observer, Serializable {

    private static final long serialVersionUID = -5547896474378477025L;
    private Server server;
    private final IGame currentGame;
    private final ActionManager actionManager;
    private ILobbyManager lobbyManager;

    private boolean gameStarted = false;
    private boolean firstTurn;
    private Map<String, VirtualView> virtualViewLog;
    private String currentPlayer;
    private VirtualView currentView;

    public void setCurrentPlayer(String player) {
        this.currentPlayer = player;
        this.currentView = virtualViewLog.get(player);
    }

    /**
     * Constructor of the game manager.
     */
    public GameManager() {

        this.currentGame = PlayingGame.getGameInstance();
        this.actionManager = new ActionManager(currentGame, this);

        this.firstTurn = true;
        this.virtualViewLog = new HashMap<>();
    }

    /**
     * Lobby manager is set later in the game when a lobby size message is sent.
     *
     * @param gameMode : decides whether the LobbyManager will be for a single player game or multiplayer.
     *                 1 = single player game;
     *                 2 = multiplayer game;
     */
    public void setLobbyManager(String gameMode) {

        if (gameMode.equals("singlePlayer")) {
            lobbyManager = new SinglePlayerLobbyManager(currentGame);
        }
        if (gameMode.equals("multiPlayer")) {
            lobbyManager = new MultiPlayerLobbyManager(this);
        }
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public IGame getCurrentGame() {
        return currentGame;
    }

    public ILobbyManager getLobbyManager() {
        return lobbyManager;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public void addVirtualView(String player, VirtualView virtualView) {
        virtualViewLog.put(player, virtualView);
    }

    /**
     * Method to end the game. Broadcasts the outcome of the match and
     *
     * @param message: end game message
     */
    public void endGame(String message) {
        lobbyManager.broadCastWinMessage(message);
        //computes scores and such to show
    }

    /**
     * Sets the boolean value gameStarted to true.
     */
    public void setGameStarted() {
        gameStarted = true;
    }

    /**
     * Returns the boolean value of gameStarted
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Method that moves the turn forward, acts as an FSM that decides what actions can be taken
     *
     * @param message: message to be processed, validated by the state of the game and the sender username
     */
    public void onMessage(Message message) {
        switch (currentGame.getCurrentState().getGameState()) {
            case SETUP:

                //it's the first turn and a nickname request is sent by the client.
                if (firstTurn && message.getMessageType().equals(PossibleMessages.SEND_NICKNAME)) {

                    //The client isn't logged in yet. He needs to send a lobby size message.
                    currentGame.setCurrentState(PossibleGameStates.SETUP_SIZE);

                    // the first turn has passed, adding all new players
                } else if (!firstTurn && message.getMessageType().equals(PossibleMessages.SEND_NICKNAME)) {
                    lobbyManager.addNewPlayer(message.getSenderUsername(), virtualViewLog.get(message.getSenderUsername()));

                    //if the last player is logged, the game can finally start its setup phase
                    if (lobbyManager.getRealPlayerList().size() == lobbyManager.getLobbySize()) {
                        currentGame.setCurrentState(PossibleGameStates.SETUP_RESOURCES);
                        lobbyManager.setPlayingOrder();

                    }
                }
                break;


            case SETUP_SIZE:
                if (message.getMessageType().equals(PossibleMessages.GAME_SIZE) && firstTurn) {
                    OneIntMessage oneIntMessage = (OneIntMessage) message;
                    if (oneIntMessage.getIndex() == 1) {

                        //creates a new single player lobby
                        setLobbyManager("singlePlayer");
                        lobbyManager.addNewPlayer(message.getSenderUsername(), virtualViewLog.get(message.getSenderUsername()));
                        lobbyManager.setPlayingOrder();
                        currentGame.setCurrentState(PossibleGameStates.SETUP_LEADER);
                    } else {

                        //creates a new multi player lobby manager.
                        setLobbyManager("multiPlayer");
                        MultiPlayerLobbyManager currentLobby = (MultiPlayerLobbyManager) lobbyManager;
                        currentLobby.setLobbySize(oneIntMessage.getIndex());
                        lobbyManager.addNewPlayer(message.getSenderUsername(), virtualViewLog.get(message.getSenderUsername()));
                        currentGame.setCurrentState(PossibleGameStates.SETUP);
                    }
                    firstTurn = false;
                    server.sizeHasBeenSet();
                }
                break;

            case SETUP_RESOURCES:
                if (message.getMessageType().equals(PossibleMessages.SETUP_RESOURCES) && message.getSenderUsername().equals(currentPlayer)) {
                    SetupResourceAnswer setupResourceAnswer = (SetupResourceAnswer) message;
                    int listSize = setupResourceAnswer.getResourcesToSet();

                    if (listSize > 0) {

                        //da problemi
                        LinkedList<Resource> obtained = ResourceBuilder.build((LinkedList<ResourceType>) setupResourceAnswer.getResourceTypes());


                        for (Resource iterator : obtained) {
                            try {
                                currentGame.getCurrentPlayer().getInventoryManager().getWarehouse().addResource((MaterialResource) iterator);
                            } catch (DiscardResourceException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if ((lobbyManager.turnOfPlayer(currentPlayer)+1) == lobbyManager.getLobbySize()) {
                        currentGame.setCurrentState(PossibleGameStates.SETUP_LEADER);
                    }
                    lobbyManager.setNextTurn();
                }
                break;

            case SETUP_LEADER:

                if(message.getMessageType().equals(PossibleMessages.SETUP_LEADERS)) {

                    DiscardTwoLeaders d = (DiscardTwoLeaders) message;

                    int l1 = d.getL1();
                    int l2 = d.getL2();

                    if(l1 > l2){

                        actionManager
                                .onReceiveAction(new DiscardLeaderCardAction(d.getSenderUsername(), l1, currentGame));
                        actionManager
                                .onReceiveAction(new DiscardLeaderCardAction(d.getSenderUsername(), l2, currentGame));
                    } else {

                        actionManager
                                .onReceiveAction(new DiscardLeaderCardAction(d.getSenderUsername(), l2, currentGame));
                        actionManager
                                .onReceiveAction(new DiscardLeaderCardAction(d.getSenderUsername(), l1, currentGame));
                    }

                    if(lobbyManager.getRealPlayerList().get(lobbyManager.getLobbySize()-1).getName().equals(currentPlayer)){
                        currentGame.setCurrentState(PossibleGameStates.PLAYING);

                    }

                    lobbyManager.setNextTurn();
                    //get leader card to discard index -> discard 2 index (greater first)



                    //if current player is the last in the ordered list

                }


        }

    }

    public void onStartTurn(){
        switch (currentGame.getCurrentState().getGameState()) {
            case SETUP_LEADER:
                currentView.showLeaderCards(currentGame.getCurrentPlayer().getOwnedLeaderCards());
                currentView.askToDiscard();
                break;

            case SETUP_RESOURCES:
                MultiPlayerLobbyManager lobby = (MultiPlayerLobbyManager) lobbyManager;
                lobby.setDefaultResources(currentPlayer);
                break;

            case PLAYING:
                currentView.currentTurn("\n Choose an action to perform.");
                break;
        }
    }

    @Override
    public void update (Message message){

    }
}
