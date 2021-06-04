package it.polimi.ingsw.controller;

import it.polimi.ingsw.actions.*;
import it.polimi.ingsw.enumerations.PossibleGameStates;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.game.IGame;
import it.polimi.ingsw.model.game.PlayingGame;
import it.polimi.ingsw.model.player.PlayerState;
import it.polimi.ingsw.model.player.RealPlayer;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.model.utilities.builders.ResourceBuilder;
import it.polimi.ingsw.network.eventHandlers.Observer;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.views.IView;

import java.util.*;

/**
 * Class to manage the entire playing game. It has instances of the currentGame, LobbyManager,
 * ActionManager.
 */
public final class GameManager implements Observer {

    private final IGame currentGame;
    private final ActionManager actionManager;
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

    public IView getCurrentView() {
        return currentView;
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
     */
    public void setLobbyManager(String gameMode) {

        if (gameMode.equalsIgnoreCase("singlePlayer")) {
            lobbyManager = new SinglePlayerLobbyManager(this);
        }
        if (gameMode.equalsIgnoreCase("multiPlayer")) {
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

                        currentGame.getCurrentPlayer().visit(new BuyProductionCardAction(buy.getSenderUsername(),
                                currentGame.getGameBoard().getProductionCardMarket().getAvailableCards().get(buy.getFirstNumber()),
                                buy.getSecondNumber(), currentGame));
                        //actionManager
                        //        .onReceiveAction(new BuyProductionCardAction(buy.getSenderUsername(),
                        //                currentGame.getGameBoard().getProductionCardMarket().getAvailableCards().get(buy.getFirstNumber()),
                        //                buy.getSecondNumber(), currentGame));
                        if(currentPlayerState.getHasBoughCard()){
                            currentGame.setCurrentState(PossibleGameStates.BUY_CARD);
                        }
                        else{
                            currentView.showGenericString("\nAction could not be performed");
                        }
                    }

                    else if(message.getMessageType().equals(PossibleMessages.GET_RESOURCES)){
                        OneIntMessage get_resources = (OneIntMessage) message;
                        //actionManager
                        //        .onReceiveAction(new GetResourceFromMarketAction(get_resources.getSenderUsername(),get_resources.getIndex(), currentGame));

                        currentGame.getCurrentPlayer().visit(new GetResourceFromMarketAction(get_resources.getSenderUsername(),get_resources.getIndex(), currentGame ));

                        if(currentPlayerState.getHasPickedResources()) {

                            if(currentPlayerState.isHasTwoExchange()) {
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
                                currentGame.getCurrentPlayer().visit(new ActivateProductionAction(activate_prod.getSenderUsername(),
                                        activate_prod.getIndex(), currentGame));
                                //actionManager
                                //        .onReceiveAction(new ActivateProductionAction(activate_prod.getSenderUsername(),
                                //                activate_prod.getIndex(), currentGame));
                                break;

                            case ACTIVATE_BASE_PRODUCTION:
                                BaseProductionMessage base_prod = (BaseProductionMessage) message;
                                //actionManager
                                //        .onReceiveAction(new ActivateBaseProductionAction(base_prod.getSenderUsername(),
                                //                base_prod.getInput1(), base_prod.getInput2(), base_prod.getOutput(), currentGame));
                                currentGame.getCurrentPlayer().visit(new ActivateBaseProductionAction(base_prod.getSenderUsername(),
                                        base_prod.getInput1(), base_prod.getInput2(), base_prod.getOutput(), currentGame));
                                break;

                            case ACTIVATE_EXTRA_PRODUCTION:
                                ExtraProductionMessage extra_prod = (ExtraProductionMessage) message;
                                currentGame.getCurrentPlayer().visit(new ActivateExtraProductionAction(extra_prod.getSenderUsername(),
                                        extra_prod.getIndex(), extra_prod.getOutput(), currentGame));
                                //actionManager
                                //        .onReceiveAction(new ActivateExtraProductionAction(extra_prod.getSenderUsername(),
                                //                extra_prod.getIndex(), extra_prod.getOutput(), currentGame));
                                break;
                        }

                        currentGame.setCurrentState(PossibleGameStates.ACTIVATE_PRODUCTION);
                    }

                    else if(message.getMessageType().equals(PossibleMessages.DISCARD_LEADER)
                        && currentPlayerState.getHasPlaceableLeaders()
                        && !currentPlayerState.getGetHasPlacedLeaders()){
                        OneIntMessage discard = (OneIntMessage) message;
                        currentGame.getCurrentPlayer().visit(new DiscardLeaderCardAction(discard.getSenderUsername(), discard.getIndex(), currentGame));
                        //actionManager
                        //        .onReceiveAction(new DiscardLeaderCardAction(discard.getSenderUsername(), discard.getIndex(), currentGame));
                    }

                    else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_LEADER)
                        && currentPlayerState.getHasPlaceableLeaders()
                        && !currentPlayerState.getGetHasPlacedLeaders()){
                        OneIntMessage activate = (OneIntMessage) message;
                        currentGame.getCurrentPlayer().visit(new PlaceLeaderAction(activate.getSenderUsername(), activate.getIndex(), currentGame));
                        //actionManager
                        //        .onReceiveAction(new PlaceLeaderAction(activate.getSenderUsername(), activate.getIndex(), currentGame));
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

    private void onSetupResources(Message message) {
        if (message.getMessageType().equals(PossibleMessages.SETUP_RESOURCES) && message.getSenderUsername().equals(currentPlayer)) {
            SetupResourceAnswer setupResourceAnswer = (SetupResourceAnswer) message;
            int listSize = setupResourceAnswer.getResourcesToSet();

            if (listSize > 0) {

                LinkedList<Resource> obtained = ResourceBuilder.build((LinkedList<ResourceType>) setupResourceAnswer.getResourceTypes());

                for (Resource iterator : obtained) {
                    iterator.deposit(currentGame.getCurrentPlayer().getPlayerBoard());
                    actionManager
                            .onReceiveAction( new DepositAction(0, message.getSenderUsername(), currentGame) );
                }
            }
            if ((lobbyManager.turnOfPlayer(currentPlayer)+1) == lobbyManager.getLobbySize()) {
                currentGame.setCurrentState(PossibleGameStates.SETUP_LEADER);
            }
            lobbyManager.setNextTurn();
        }
    }

    private void onSetupLeader(Message message) {
        if(message.getMessageType().equals(PossibleMessages.SETUP_LEADERS)) {

            DiscardTwoLeaders d = (DiscardTwoLeaders) message;

            int l1 = d.getL1();
            int l2 = d.getL2();

            if(l1 > l2){

                //actionManager
                //        .onReceiveAction(new DiscardLeaderCardAction(d.getSenderUsername(), l1, currentGame));
                //actionManager
                //        .onReceiveAction(new DiscardLeaderCardAction(d.getSenderUsername(), l2, currentGame));
                currentGame.getCurrentPlayer().visit(new DiscardLeaderCardAction(d.getSenderUsername(), l1, currentGame) );
                currentGame.getCurrentPlayer().visit(new DiscardLeaderCardAction(d.getSenderUsername(), l2, currentGame));
            } else {

                //actionManager
                //        .onReceiveAction(new DiscardLeaderCardAction(d.getSenderUsername(), l2, currentGame));

                //actionManager
                //        .onReceiveAction(new DiscardLeaderCardAction(d.getSenderUsername(), l1, currentGame));
                currentGame.getCurrentPlayer().visit(new DiscardLeaderCardAction(d.getSenderUsername(), l2, currentGame) );
                currentGame.getCurrentPlayer().visit(new DiscardLeaderCardAction(d.getSenderUsername(), l1, currentGame));
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

    private void onActivateProduction(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)) {
            if(message.getMessageType().equals(PossibleMessages.ACTIVATE_PRODUCTION)) {
                OneIntMessage activate_prod = (OneIntMessage) message;
                currentGame.getCurrentPlayer().visit(new ActivateProductionAction(activate_prod.getSenderUsername(),
                        activate_prod.getIndex(), currentGame));
                //actionManager
                //        .onReceiveAction(new ActivateProductionAction(activate_prod.getSenderUsername(),
                //                activate_prod.getIndex(), currentGame));
            }

            else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_BASE_PRODUCTION)) {
                BaseProductionMessage base_prod = (BaseProductionMessage) message;
                //actionManager
                //        .onReceiveAction(new ActivateBaseProductionAction(base_prod.getSenderUsername(),
                //                base_prod.getInput1(), base_prod.getInput2(), base_prod.getOutput(), currentGame));
                currentGame.getCurrentPlayer().visit(new ActivateBaseProductionAction(base_prod.getSenderUsername(),
                        base_prod.getInput1(), base_prod.getInput2(), base_prod.getOutput(), currentGame));
            }

            else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_EXTRA_PRODUCTION)){
                ExtraProductionMessage extra_prod = (ExtraProductionMessage) message;
                currentGame.getCurrentPlayer().visit(new ActivateExtraProductionAction(extra_prod.getSenderUsername(),
                        extra_prod.getIndex(), extra_prod.getOutput(), currentGame));
                //actionManager
                //        .onReceiveAction(new ActivateExtraProductionAction(extra_prod.getSenderUsername(),
                //                extra_prod.getIndex(), extra_prod.getOutput(), currentGame));
            }
            else if(message.getMessageType().equals(PossibleMessages.EXECUTE_PRODUCTION)){
                currentGame.getCurrentPlayer().visit(new ExecuteProductionAction(message.getSenderUsername(), currentGame));
                //actionManager
                //        .onReceiveAction(new ExecuteProductionAction(message.getSenderUsername(), currentGame));
                if(currentGame.getCurrentPlayer().getPlayerState().hasPerformedExclusiveAction()){
                    currentGame.setCurrentState(PossibleGameStates.REMOVE);
                } else {
                    currentGame.setCurrentState(PossibleGameStates.PLAYING);
                }
            }
            onStartTurn();
        }
    }

    private void onBuyCard(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)) {

            ArrayList<ResourceTag> toBeRemoved = currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().getToBeRemoved();

            if (message.getMessageType().equals(PossibleMessages.SOURCE_STRONGBOX)){
                SourceStrongboxMessage strongboxMessage = (SourceStrongboxMessage) message;
                currentGame.getCurrentPlayer().visit(new RemoveResourcesAction(strongboxMessage.getSenderUsername(), "STRONGBOX", toBeRemoved.get(0), currentGame));
                //actionManager
                //        .onReceiveAction(new RemoveResourcesAction(strongboxMessage.getSenderUsername(), "STRONGBOX", toBeRemoved.get(0), currentGame));
                currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().getToBeRemoved().remove(toBeRemoved.get(0));
            }

            else if(message.getMessageType().equals(PossibleMessages.SOURCE_WAREHOUSE)){
                SourceWarehouseMessage warehouseMessage = (SourceWarehouseMessage) message;
                currentGame.getCurrentPlayer().visit(new RemoveResourcesAction(warehouseMessage.getSenderUsername(),"WAREHOUSE" , toBeRemoved.get(0), currentGame));
                //actionManager
                //        .onReceiveAction(new RemoveResourcesAction(warehouseMessage.getSenderUsername(),"WAREHOUSE" , toBeRemoved.get(0), currentGame));
                currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().getToBeRemoved().remove(toBeRemoved.get(0));
            }

            if(toBeRemoved.isEmpty())
                currentGame.setCurrentState(PossibleGameStates.MAIN_ACTION_DONE);

            onStartTurn();
        }
    }

    private void onChangeColor(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)){

            if(message.getMessageType().equals(PossibleMessages.RESOURCE)){
                ExchangeResourceMessage colorChange = (ExchangeResourceMessage) message;
                currentGame.getCurrentPlayer().visit(new ChangeMarbleAction(colorChange.getSenderUsername(),
                        colorChange.getExchangeWithThis(), colorChange.getIndex(), currentGame));
                //actionManager
                //        .onReceiveAction(new ChangeMarbleAction(colorChange.getSenderUsername(),
                //                colorChange.getExchangeWithThis(), colorChange.getIndex(),currentGame));
            }

            if(currentPlayerState.CanDeposit()) {
                currentGame.setCurrentState(PossibleGameStates.DEPOSIT);
            }
            onStartTurn();
        }
    }

    private void onDeposit(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)){
            if(message.getMessageType().equals(PossibleMessages.DEPOSIT)){
                OneIntMessage deposit = (OneIntMessage) message;
                //actionManager
                //        .onReceiveAction(new DepositAction(deposit.getIndex(), deposit.getSenderUsername(), currentGame));
                currentGame.getCurrentPlayer().visit(new DepositAction(deposit.getIndex(), deposit.getSenderUsername(), currentGame));
                if(currentGame.getCurrentPlayer().getPlayerBoard().getInventoryManager().getBuffer().isEmpty()){
                    currentGame.setCurrentState(PossibleGameStates.MAIN_ACTION_DONE);
                }
            }
        }
        onStartTurn();
    }

    private void onRemove(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)) {

            ArrayList<ResourceTag> toBeRemoved = currentGame.getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources();

            //if (toBeRemoved.isEmpty()){
            //    currentGame.setCurrentState(PossibleGameStates.MAIN_ACTION_DONE);
            //    onStartTurn();
            //}

            if (message.getMessageType().equals(PossibleMessages.SOURCE_STRONGBOX)){
                SourceStrongboxMessage strongboxMessage = (SourceStrongboxMessage) message;
                currentGame.getCurrentPlayer().visit(new RemoveResourcesAction(strongboxMessage.getSenderUsername(), "STRONGBOX", toBeRemoved.get(0), currentGame));
                //actionManager
                //        .onReceiveAction(new RemoveResourcesAction(strongboxMessage.getSenderUsername(), "STRONGBOX", toBeRemoved.get(0), currentGame));
                currentGame.getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().remove(toBeRemoved.get(0));

            }

            else if(message.getMessageType().equals(PossibleMessages.SOURCE_WAREHOUSE)){
                SourceWarehouseMessage warehouseMessage = (SourceWarehouseMessage) message;
                currentGame.getCurrentPlayer().visit(new RemoveResourcesAction(warehouseMessage.getSenderUsername(),"WAREHOUSE" , toBeRemoved.get(0), currentGame));
                //actionManager
                //        .onReceiveAction(new RemoveResourcesAction(warehouseMessage.getSenderUsername(),"WAREHOUSE" , toBeRemoved.get(0), currentGame));
                currentGame.getCurrentPlayer().getPlayerBoard().getProductionBoard().getFinalProduction().getInputResources().remove(toBeRemoved.get(0));

            }

            if (toBeRemoved.isEmpty()){
                currentGame.setCurrentState(PossibleGameStates.MAIN_ACTION_DONE);

            }
            onStartTurn();
        }
    }

    private void onMainActionDone(Message message) {
        if(message.getSenderUsername().equals(currentPlayer)){
            if(message.getMessageType().equals(PossibleMessages.END_TURN)){
                currentGame.getCurrentPlayer().visit(new EndTurnAction(message.getSenderUsername(), currentGame));
                //currentPlayerState.endTurnReset();
                currentGame.setCurrentState(PossibleGameStates.PLAYING);
                lobbyManager.setNextTurn();
            }

            else if(message.getMessageType().equals(PossibleMessages.DISCARD_LEADER)
                    && currentPlayerState.getHasPlaceableLeaders()
                    && !currentPlayerState.getGetHasPlacedLeaders()){
                OneIntMessage discard = (OneIntMessage) message;
                currentGame.getCurrentPlayer().visit(new DiscardLeaderCardAction(discard.getSenderUsername(), discard.getIndex(), currentGame));
                //actionManager
                //        .onReceiveAction(new DiscardLeaderCardAction(discard.getSenderUsername(), discard.getIndex(), currentGame));
                onStartTurn();
            }

            else if(message.getMessageType().equals(PossibleMessages.ACTIVATE_LEADER)
                    && currentPlayerState.getHasPlaceableLeaders()
                    && !currentPlayerState.getGetHasPlacedLeaders()){
                OneIntMessage activate = (OneIntMessage) message;
                currentGame.getCurrentPlayer().visit(new PlaceLeaderAction(activate.getSenderUsername(), activate.getIndex(), currentGame));
                //actionManager
                //        .onReceiveAction(new PlaceLeaderAction(activate.getSenderUsername(), activate.getIndex(), currentGame));
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
                currentView.currentTurn("\nThe production was successful! Type 'REMOVE' followed by the source.");
                break;

            case MAIN_ACTION_DONE:
                currentView.currentTurn("\nMain action performed you can place/discard leaders or peek on other players.");
                break;

            default: currentView.currentTurn("\nYour command cannot be processed now, please try a different one");
        }
    }

    /**
     * Sends specific update messages depending on what part of the model has been updated.
     * @param message: model update message.
     */
    @Override
    public void update (Message message){

    }

    /**
     * Resets the variable of the controller and game instance.
     */
    public void resetFSM() {
        Server.LOGGER.info("Game ended, preparing the server for a new game . . .");
        PlayingGame.terminate();

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
