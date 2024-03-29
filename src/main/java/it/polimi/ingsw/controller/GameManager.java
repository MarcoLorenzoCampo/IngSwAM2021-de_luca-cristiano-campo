package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.*;
import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.player.PlayerState;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.model.utilities.builders.ResourceBuilder;
import it.polimi.ingsw.eventHandlers.Observer;
import it.polimi.ingsw.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.*;
import it.polimi.ingsw.network.server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Class to manage the entire playing game. It has instances of the currentGame, LobbyManager.
 */
public final class GameManager {

    private final IGame currentGame;
    private ILobbyManager lobbyManager;

    private boolean gameStarted = false;
    private boolean firstTurn;
    private final Map<String, VirtualView> virtualViewLog;

    private String currentPlayer;
    private VirtualView currentView;
    private PlayerState currentPlayerState;

    public void setCurrentPlayer(String player) {
        this.currentPlayer = player;
        this.currentView = virtualViewLog.get(player);
        this.currentPlayerState = currentGame.getCurrentPlayer().getPlayerState();
    }

    public VirtualView getCurrentView() {
        return currentView;
    }

    /**
     * Constructor of the game manager.
     */
    public GameManager() {

        this.currentGame = PlayingGame.getGameInstance();

        this.firstTurn = true;
        this.virtualViewLog = new HashMap<>();
    }

    /**
     * Lobby manager is set later in the game when a lobby size message is sent.
     *
     * @param gameMode : decides whether the LobbyManager will be for a single player game or multiplayer.
     */
    public void setLobbyManager(String gameMode) {

        if (gameMode.equalsIgnoreCase("singlePlayer")) {
            lobbyManager = new SinglePlayerLobbyManager(this);
        }
        if (gameMode.equalsIgnoreCase("multiPlayer")) {
            lobbyManager = new MultiPlayerLobbyManager(this);
        }
    }

    public IGame getCurrentGame() {
        return currentGame;
    }

    public ILobbyManager getLobbyManager() {
        return lobbyManager;
    }

    /**
     * Each time a client is connected, his virtual view is set as an Observer {@link Observer},
     * so that he can be notified when changes occur.
     * @param player: Name of the player associated to the virtual view.
     * @param virtualView: virtual view, observer.
     */
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
        resetFSM();
    }

    public String getCurrentPlayer() {
        return currentPlayer;
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
     * Method that moves the turn forward, acts as an FSM that decides what actions can be taken.
     *
     * @param message: message to be processed, validated by the state of the game and the sender username
     */
    public void onMessage(Message message) {
        switch (currentGame.getCurrentState().getGameState()) {
            case SETUP:
                onSetup(message);
                break;

            case SETUP_SIZE:
                onSetupSize(message);
                break;

            case SETUP_RESOURCES:
                onSetupResources(message);
                break;

            case SETUP_LEADER:
                onSetupLeader(message);
                break;

            case PLAYING:
                if(message.getSenderUsername().equals(currentPlayer)) {

                    if(message.getMessageType().equals(PossibleMessages.BUY_PRODUCTION)){
                        TwoIntMessage buy = (TwoIntMessage) message;


                        new BuyProductionCardAction(buy.getSenderUsername(),
                                currentGame.getGameBoard().getProductionCardMarket().getAvailableCards().get(buy.getFirstNumber()),
                                buy.getSecondNumber())
                                .accept(currentGame.getCurrentPlayer());


                        if(currentPlayerState.getHasBoughCard()){
                            currentGame.setCurrentState(PossibleGameStates.BUY_CARD);
                        }
                        else{
                            currentView.showGenericString("\nAction could not be performed");
                        }
                    }

                    else if(message.getMessageType().equals(PossibleMessages.GET_RESOURCES)){
                        OneIntMessage get_resources = (OneIntMessage) message;
                        new GetResourceFromMarketAction(get_resources.getSenderUsername(),get_resources.getIndex()).accept(currentGame.getCurrentPlayer());
                        //currentGame.getCurrentPlayer().visit(new GetResourceFromMarketAction(get_resources.getSenderUsername(),get_resources.getIndex(), currentGame ));

                        if(currentPlayerState.getHasPickedResources()) {

                            if(currentPlayerState.isHasTwoExchange()&& !currentPlayerState.CanDeposit()) {
                                currentGame.setCurrentState(PossibleGameStates.CHANGE_COLOR);
                            }

                            else {
                                currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().whiteMarblesExchange();
                                currentGame.setCurrentState(PossibleGameStates.DEPOSIT);
                            }
                        }

                        else {
                            currentGame.setCurrentState(PossibleGameStates.PLAYING);
                        }
                    }

                    else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_BASE_PRODUCTION)
                    || message.getMessageType().equals(PossibleMessages.ACTIVATE_PRODUCTION)
                    || message.getMessageType().equals(PossibleMessages.ACTIVATE_EXTRA_PRODUCTION)){

                        switch (message.getMessageType()){
                            case ACTIVATE_PRODUCTION:
                                OneIntMessage activate_prod = (OneIntMessage) message;

                                new ActivateProductionAction(activate_prod.getSenderUsername(),activate_prod.getIndex()).accept(currentGame.getCurrentPlayer());


                                break;

                            case ACTIVATE_BASE_PRODUCTION:
                                BaseProductionMessage base_prod = (BaseProductionMessage) message;
                                new ActivateBaseProductionAction(base_prod.getSenderUsername(),
                                        base_prod.getInput1(), base_prod.getInput2(), base_prod.getOutput()).accept(currentGame.getCurrentPlayer());

                                break;

                            case ACTIVATE_EXTRA_PRODUCTION:
                                ExtraProductionMessage extra_prod = (ExtraProductionMessage) message;
                                new ActivateExtraProductionAction(extra_prod.getSenderUsername(),
                                        extra_prod.getIndex(), extra_prod.getOutput()).accept(currentGame.getCurrentPlayer());


                                break;
                        }

                        currentGame.setCurrentState(PossibleGameStates.ACTIVATE_PRODUCTION);
                    }

                    else if(message.getMessageType().equals(PossibleMessages.DISCARD_LEADER)
                        && currentPlayerState.getHasPlaceableLeaders()
                        && !currentPlayerState.getGetHasPlacedLeaders()){
                        OneIntMessage discard = (OneIntMessage) message;
                        new DiscardLeaderCardAction(discard.getSenderUsername(), discard.getIndex()).accept(currentGame.getCurrentPlayer());
                        //currentGame.getCurrentPlayer().visit(new DiscardLeaderCardAction(discard.getSenderUsername(), discard.getIndex(), currentGame));
                    }

                    else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_LEADER)
                        && currentPlayerState.getHasPlaceableLeaders()
                        && !currentPlayerState.getGetHasPlacedLeaders()){
                        OneIntMessage activate = (OneIntMessage) message;
                        new PlaceLeaderAction(activate.getSenderUsername(), activate.getIndex()).accept(currentGame.getCurrentPlayer());
                        //currentGame.getCurrentPlayer().visit(new PlaceLeaderAction(activate.getSenderUsername(), activate.getIndex(), currentGame));
                    }
                }
                onStartTurn();
                break;

            case BUY_CARD:
                onBuyCard(message);
                break;

            case CHANGE_COLOR:
                onChangeColor(message);
                break;

            case DEPOSIT:
                onDeposit(message);
                break;

            case ACTIVATE_PRODUCTION:
                onActivateProduction(message);
                break;

            case REMOVE:
                onRemove(message);
                break;

            case MAIN_ACTION_DONE:
                onMainActionDone(message);
                break;
        }
    }


    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is SETUP. It initializes the players and asks the first player to
     * set the game size
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onSetup(Message message) {
        //it's the first turn and a nickname request is sent by the client.
        if (firstTurn && message.getMessageType().equals(PossibleMessages.SEND_NICKNAME)) {

            //The client isn't logged in yet. He needs to send a lobby size message.
            currentGame.setCurrentState(PossibleGameStates.SETUP_SIZE);

            // the first turn has passed, adding all new players
        } else if (!firstTurn && message.getMessageType().equals(PossibleMessages.SEND_NICKNAME)) {
            lobbyManager.addNewPlayer(message.getSenderUsername(), virtualViewLog.get(message.getSenderUsername()));

            //if the last player is logged, the game can finally start its setup phase
            if (lobbyManager.getRealPlayerList().size() == lobbyManager.getLobbySize()) {
                setGameStarted();
                currentGame.setCurrentState(PossibleGameStates.SETUP_RESOURCES);
                lobbyManager.setPlayingOrder();
            }
        }
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is SETUP_SIZE. Accepts only the message that has the
     * game size. After the game size is set returns to the setup phase
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onSetupSize(Message message) {
        if (message.getMessageType().equals(PossibleMessages.GAME_SIZE) && firstTurn) {
            OneIntMessage oneIntMessage = (OneIntMessage) message;
            if (oneIntMessage.getIndex() == 1) {

                //creates a new single player lobby
                setLobbyManager("singlePlayer");
                lobbyManager.addNewPlayer(message.getSenderUsername(), virtualViewLog.get(message.getSenderUsername()));
                lobbyManager.setPlayingOrder();
                currentGame.setCurrentState(PossibleGameStates.SETUP_LEADER);
                onStartTurn();
            } else {

                //creates a new multi player lobby manager.
                setLobbyManager("multiPlayer");
                MultiPlayerLobbyManager currentLobby = (MultiPlayerLobbyManager) lobbyManager;
                currentLobby.setLobbySize(oneIntMessage.getIndex());
                lobbyManager.addNewPlayer(message.getSenderUsername(), virtualViewLog.get(message.getSenderUsername()));
                currentGame.setCurrentState(PossibleGameStates.SETUP);
            }
            firstTurn = false;
        }
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is SETUP_RESOURCES. Gives the player the resource
     * they have chosen
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onSetupResources(Message message) {
        if (message.getMessageType().equals(PossibleMessages.SETUP_RESOURCES) && message.getSenderUsername().equals(currentPlayer)) {
            SetupResourceAnswer setupResourceAnswer = (SetupResourceAnswer) message;
            int listSize = setupResourceAnswer.getResourcesToSet();

            if (listSize > 0) {

                LinkedList<Resource> obtained = ResourceBuilder.build((LinkedList<ResourceType>) setupResourceAnswer.getResourceTypes());

                for (Resource iterator : obtained) {
                    iterator.deposit(currentGame.getCurrentPlayer().getPlayerBoard());
                    new DepositAction(0, message.getSenderUsername()).accept(currentGame.getCurrentPlayer());
                }
            }
            if ((lobbyManager.turnOfPlayer(currentPlayer)+1) == lobbyManager.getLobbySize()) {
                currentGame.setCurrentState(PossibleGameStates.SETUP_LEADER);
            }
            lobbyManager.setNextTurn();
        }
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is SETUP_LEADERS, initializes the owned leader cards
     * of each player based on the ones the player has sent to discard
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onSetupLeader(Message message) {
        if(message.getMessageType().equals(PossibleMessages.SETUP_LEADERS)) {

            DiscardTwoLeaders d = (DiscardTwoLeaders) message;

            int l1 = d.getL1();
            int l2 = d.getL2();

            if(l1 > l2){

                new DiscardLeaderCardAction(d.getSenderUsername(), l1).accept(currentGame.getCurrentPlayer());
                new DiscardLeaderCardAction(d.getSenderUsername(), l2).accept(currentGame.getCurrentPlayer());

            } else {
                new DiscardLeaderCardAction(d.getSenderUsername(), l2).accept(currentGame.getCurrentPlayer());
                new DiscardLeaderCardAction(d.getSenderUsername(), l1).accept(currentGame.getCurrentPlayer());

            }

            if(lobbyManager.getRealPlayerList().get(lobbyManager.getLobbySize()-1).getName().equals(currentPlayer)) {
                currentGame.setCurrentState(PossibleGameStates.PLAYING);

                //Before the game starts, if a player got disconnected before discarding 2 leaders,
                // they get randomly selected and discarded.
                for(RealPlayer realPlayer : lobbyManager.getRealPlayerList()) {
                    if(!realPlayer.getPlayerState().isConnected() &&
                            realPlayer.getOwnedLeaderCards().size() == 4) {

                        lobbyManager.randomizedLeadersSetup(realPlayer.getName());
                    }
                }
            }

            currentPlayerState.setSetUpPhase(false);
            lobbyManager.setNextTurn();
        }
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is ACTIVATE_PRODUCTION. Accepts only messages that
     * activate another production to activate or a message to activate the final production.
     * if the production is executed than the state becomes REMOVE otherwise it returns to PLAYING
     * and acts as if the player hasn't chosen yet which action to perform
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onActivateProduction(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)) {
            if(message.getMessageType().equals(PossibleMessages.ACTIVATE_PRODUCTION)) {
                OneIntMessage activate_prod = (OneIntMessage) message;

                new ActivateProductionAction(activate_prod.getSenderUsername(),
                        activate_prod.getIndex()).accept(currentGame.getCurrentPlayer());

            }

            else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_BASE_PRODUCTION)) {
                BaseProductionMessage base_prod = (BaseProductionMessage) message;
                new ActivateBaseProductionAction(base_prod.getSenderUsername(),
                        base_prod.getInput1(), base_prod.getInput2(), base_prod.getOutput()).accept(currentGame.getCurrentPlayer());

            }

            else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_EXTRA_PRODUCTION)){
                ExtraProductionMessage extra_prod = (ExtraProductionMessage) message;

                new ActivateExtraProductionAction(extra_prod.getSenderUsername(),
                        extra_prod.getIndex(), extra_prod.getOutput()).accept(currentGame.getCurrentPlayer());

            }
            else if(message.getMessageType().equals(PossibleMessages.EXECUTE_PRODUCTION)){
                new ExecuteProductionAction(message.getSenderUsername()).accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new ExecuteProductionAction(message.getSenderUsername(), currentGame));

                if(currentGame.getCurrentPlayer().getPlayerState().hasPerformedExclusiveAction()){
                    currentGame.setCurrentState(PossibleGameStates.REMOVE);
                } else {
                    currentGame.setCurrentState(PossibleGameStates.PLAYING);
                    new ClearProductionAction().accept(currentGame.getCurrentPlayer());
                    //currentGame.getCurrentPlayer().visit(new ClearProductionAction());
                }
            }
            onStartTurn();
        }
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is BUY_CARD. Accepts only messages to remove resources
     * to pay for the production card bought
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onBuyCard(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)) {

            ArrayList<ResourceTag> toBeRemoved = currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().getToBeRemoved();

            if (message.getMessageType().equals(PossibleMessages.SOURCE_STRONGBOX)){
                SourceStrongboxMessage strongboxMessage = (SourceStrongboxMessage) message;
                new RemoveResourcesAction(strongboxMessage.getSenderUsername(), "STRONGBOX", toBeRemoved.get(0)).accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new RemoveResourcesAction(strongboxMessage.getSenderUsername(), "STRONGBOX", toBeRemoved.get(0), currentGame));
                currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().getToBeRemoved().remove(toBeRemoved.get(0));
            }

            else if(message.getMessageType().equals(PossibleMessages.SOURCE_WAREHOUSE)){
                SourceWarehouseMessage warehouseMessage = (SourceWarehouseMessage) message;
                new RemoveResourcesAction(warehouseMessage.getSenderUsername(),"WAREHOUSE" , toBeRemoved.get(0)).accept(currentGame.getCurrentPlayer());
                currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().getToBeRemoved().remove(toBeRemoved.get(0));
            }

            if(toBeRemoved.isEmpty())
                currentGame.setCurrentState(PossibleGameStates.MAIN_ACTION_DONE);

            onStartTurn();
        }
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is CHANGE_COLOR. The game goes into this state only if
     * the current player has more than one exchange power. Accepts only messages to change the color
     * of white marbles, it changes the state only when the buffer doesn't hold any white marble
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onChangeColor(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)){

            if(message.getMessageType().equals(PossibleMessages.EXCHANGE_RESOURCE)){
                ExchangeResourceMessage colorChange = (ExchangeResourceMessage) message;
                new ChangeMarbleAction(colorChange.getSenderUsername(), colorChange.getExchangeWithThis(), colorChange.getIndex()).accept(currentGame.getCurrentPlayer());

            }

            if(currentPlayerState.CanDeposit()) {
                currentGame.setCurrentState(PossibleGameStates.DEPOSIT);
            }
            onStartTurn();
        }
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is DEPOSIT. Accepts only deposit actions, doesn't move
     * forward until the buffer is empty
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onDeposit(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)){
            if(message.getMessageType().equals(PossibleMessages.DEPOSIT)){
                OneIntMessage deposit = (OneIntMessage) message;
                new DepositAction(deposit.getIndex(), deposit.getSenderUsername()).accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new DepositAction(deposit.getIndex(), deposit.getSenderUsername(), currentGame));
                if(currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().isEmpty()){
                    currentGame.setCurrentState(PossibleGameStates.MAIN_ACTION_DONE);
                }
            }
        }
        onStartTurn();
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is REMOVE. Accepts only remove actions, doesn't move until
     * all input resources in the final production have been removed from the player's inventory
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onRemove(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)) {

            ArrayList<ResourceTag> toBeRemoved = currentGame.getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources();

            if (message.getMessageType().equals(PossibleMessages.SOURCE_STRONGBOX)){
                SourceStrongboxMessage strongboxMessage = (SourceStrongboxMessage) message;
                new RemoveResourcesAction(strongboxMessage.getSenderUsername(), "STRONGBOX", toBeRemoved.get(0)).accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new RemoveResourcesAction(strongboxMessage.getSenderUsername(), "STRONGBOX", toBeRemoved.get(0), currentGame));
                currentGame.getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().remove(toBeRemoved.get(0));

            }

            else if(message.getMessageType().equals(PossibleMessages.SOURCE_WAREHOUSE)){
                SourceWarehouseMessage warehouseMessage = (SourceWarehouseMessage) message;
                new RemoveResourcesAction(warehouseMessage.getSenderUsername(),"WAREHOUSE" , toBeRemoved.get(0)).accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new RemoveResourcesAction(warehouseMessage.getSenderUsername(),"WAREHOUSE" , toBeRemoved.get(0), currentGame));
                currentGame.getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().remove(toBeRemoved.get(0));

            }

            if (toBeRemoved.isEmpty()){
                currentGame.setCurrentState(PossibleGameStates.MAIN_ACTION_DONE);
                new ClearProductionAction().accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new ClearProductionAction());

            }
            onStartTurn();
        }
    }

    /**
     * Method that defines what the controller needs to do based on the message received,
     * knowing that the state of the game is MAIN_ACTION_DONE. Represents the state in which the player has
     * executed one main action, the player can now either discard/place a leader card or end their turn
     *
     * @param message: message received from the player, holds the information on the action
     *                the player wants to take
     */
    private void onMainActionDone(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)){
            if(message.getMessageType().equals(PossibleMessages.END_TURN)){
                new EndTurnAction(message.getSenderUsername()).accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new EndTurnAction(message.getSenderUsername(), currentGame));
                currentGame.setCurrentState(PossibleGameStates.PLAYING);
                lobbyManager.setNextTurn();
            }

            else if(message.getMessageType().equals(PossibleMessages.DISCARD_LEADER)
                    && currentPlayerState.getHasPlaceableLeaders()
                    && !currentPlayerState.getGetHasPlacedLeaders()){
                OneIntMessage discard = (OneIntMessage) message;
                new DiscardLeaderCardAction(discard.getSenderUsername(), discard.getIndex()).accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new DiscardLeaderCardAction(discard.getSenderUsername(), discard.getIndex(), currentGame));
                onStartTurn();
            }

            else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_LEADER)
                    && currentPlayerState.getHasPlaceableLeaders()
                    && !currentPlayerState.getGetHasPlacedLeaders()){
                OneIntMessage activate = (OneIntMessage) message;
                new PlaceLeaderAction(activate.getSenderUsername(), activate.getIndex()).accept(currentGame.getCurrentPlayer());
                //currentGame.getCurrentPlayer().visit(new PlaceLeaderAction(activate.getSenderUsername(), activate.getIndex(), currentGame));
                onStartTurn();
            }
            else onStartTurn();
        }
    }

    /**
     * This method associates each game state with an action to perform on the player's view. Sets the beginning
     * phase of the game and iterates over a main "currentTurn" method during the game phase.
     */
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
                currentView.currentTurn("\nChoose an action to perform.");
                break;

            case CHANGE_COLOR:
                currentView.currentTurn("\nLooks like you can change white marbles, please select the resource type you want");
                break;

            case DEPOSIT:
                currentView.currentTurn("\n Resources have been added to your buffer! Please deposit them in your warehouse");
                break;

            case BUY_CARD:
                currentView.currentTurn("\nThe card is yours! Time to pay now");
                break;

            case ACTIVATE_PRODUCTION:
                currentView.currentTurn("\nAdding productions to your final production! Type 'EXECUTE' to start.");
                break;

            case REMOVE:
                currentView.currentTurn("\nThe production was successful! Type 'SOURCE' followed by WAREHOUSE or STRONGBOX");
                break;

            case MAIN_ACTION_DONE:
                currentView.currentTurn("\nMain action performed you can place/discard leaders or peek on other players.");
                break;

            default: currentView.currentTurn("\nYour command cannot be processed now, please try a different one");
        }
    }

    /**
     * Resets the variable of the controller and game instance.
     */
    public void resetFSM() {
        Server.LOGGER.info("Game ended, preparing the server for a new game . . .");
        PlayingGame.terminate();
        firstTurn = true;
        gameStarted = false;
        currentPlayer = "";
        currentPlayerState = null;
        currentView = null;
        virtualViewLog.clear();
        currentGame.setCurrentState(PossibleGameStates.SETUP);
        //lobbyManager = null;
    }

    /**
     * When the current player gets disconnected, a specific reset needs to be made in order to interrupt
     * any action the player was doing and reset for a new player.
     *
     * Also resets the game state to PLAYING.
     */
    public void prepareForNextTurn(RealPlayer disconnected) {
        switch (currentGame.getCurrentState().getGameState()) {

            case SETUP_LEADER:
                if(disconnected.getName().equals(currentPlayer)) {
                    lobbyManager.randomizedLeadersSetup(disconnected.getName());
                    Server.LOGGER.info("Random '" + currentPlayer + "' leaders set.");
                }
                break;

            case SETUP_RESOURCES:
                lobbyManager.randomizedResourcesSetup(disconnected.getName());
                Server.LOGGER.info("Random '" + currentPlayer + "' resources set.");
                break;

            //Buffer gets emptied.
            case CHANGE_COLOR:
                disconnected.getPlayerBoard().getInventoryManager().resetBuffer();
                Server.LOGGER.info("No more exchanges for: " + currentPlayer);
                disconnected.getPlayerState().endTurnReset();

                currentGame.setCurrentState(PossibleGameStates.PLAYING);
                break;

            //Buffer gets emptied.
            case DEPOSIT:
                disconnected.getPlayerBoard().getInventoryManager().resetBuffer();
                Server.LOGGER.info("Emptied '" + currentPlayer + "'s' buffer.");
                disconnected.getPlayerState().endTurnReset();

                currentGame.setCurrentState(PossibleGameStates.PLAYING);
                break;

            //The card is bought (because validated) and resources are removed.
            case BUY_CARD:
                disconnected.getPlayerBoard().getInventoryManager().defaultRemove(disconnected.getPlayerBoard().getInventoryManager().getToBeRemoved());
                disconnected.getPlayerBoard().getInventoryManager().getToBeRemoved().clear();
                disconnected.getPlayerState().endTurnReset();

                currentGame.setCurrentState(PossibleGameStates.PLAYING);
                break;

            //Empties the list of desired productions.
            case ACTIVATE_PRODUCTION:
                disconnected.getPlayerBoard().getProductionBoard().clearSelection();
                disconnected.getPlayerState().endTurnReset();

                currentGame.setCurrentState(PossibleGameStates.PLAYING);
                break;

            //Removes the resources to pay for productions.
            case REMOVE:
                ArrayList<ResourceTag> toBeRemoved = disconnected.getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources();
                disconnected.getPlayerBoard().getInventoryManager().defaultRemove(toBeRemoved);
                disconnected.getPlayerBoard().getProductionBoard().clearSelection();
                disconnected.getPlayerState().endTurnReset();

                currentGame.setCurrentState(PossibleGameStates.PLAYING);
                break;

            //The reset doesn't need to be made in every possible game state.
            default: break;
        }
    }

    public Map<String, VirtualView> getVirtualViewLog() {
        return virtualViewLog;
    }


}
