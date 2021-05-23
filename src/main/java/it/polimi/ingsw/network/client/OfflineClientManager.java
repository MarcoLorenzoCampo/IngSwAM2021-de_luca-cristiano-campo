package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.ILobbyManager;
import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ResourceMarket;
import it.polimi.ingsw.model.market.leaderCards.*;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.eventHandlers.Observer;
import it.polimi.ingsw.network.eventHandlers.ViewObserver;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.NicknameRequest;
import it.polimi.ingsw.network.messages.playerMessages.OneIntMessage;
import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.network.views.IView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OfflineClientManager implements ViewObserver, Observer {

    /**
     * Reference to the generic view chosen.
     */
    private final IView view;

    /**
     * Manager of the offline game.
     */
    private final GameManager gameManager;

    /**
     * Name of the client.
     */
    private String nickname;

    /**
     * Thread to update the view.
     */
    private final Executor viewUpdater;

    public OfflineClientManager(IView view) {
        this.gameManager = new GameManager();
        gameManager.setLobbyManager("singlePlayer");
        this.view = view;
        viewUpdater = Executors.newSingleThreadExecutor();
    }

    @Override
    public void update(Message message) {
        if(message != null) {
            switch (message.getMessageType()) {
                case LOBBY_SIZE_REQUEST:
                    viewUpdater.execute(view::askPlayerNumber);
                    break;

                case LOGIN_OUTCOME:
                    LoginOutcomeMessage m = (LoginOutcomeMessage) message;
                    viewUpdater.execute(() ->
                            view.showLoginOutput(m.isConnectionOutcome(), m.isNicknameAccepted(), m.isReconnected()));
                    break;

                case GENERIC_SERVER_MESSAGE:
                    GenericMessageFromServer g = (GenericMessageFromServer) message;
                    viewUpdater.execute(() -> view.showGenericString(g.getServerString()));
                    break;

                case BOARD:
                    ResourceMarketMessage r = (ResourceMarketMessage) message;
                    viewUpdater.execute(() -> view.printResourceMarket(r.getResourceBoard(), r.getExtraMarble()));
                    break;

                case BUFFER:
                    BufferMessage buffer = (BufferMessage) message;
                    viewUpdater.execute(() -> view.printBuffer(buffer.getBuffer()));
                    break;

                case STRONGBOX:
                    StrongboxMessage strongbox = (StrongboxMessage) message;
                    viewUpdater.execute(() -> view.printStrongbox(strongbox.getStrongbox()));
                    break;

                case PRODUCTION_BOARD:
                    ProductionBoardMessage productionBoard = (ProductionBoardMessage) message;
                    List<LeaderCard> leader_prod = deserializeLeaderCards(productionBoard.getExtra_productions());
                    viewUpdater.execute(() ->
                            view.showLeaderCards(leader_prod));
                    viewUpdater.execute(() -> view.printProductionBoard(productionBoard.getProductions()));
                    break;

                case WAREHOUSE:
                    WarehouseMessage warehouse = (WarehouseMessage) message;
                    viewUpdater.execute(() -> view.printWarehouse(warehouse.getWarehouse(), warehouse.getExtra_shelf()));
                    break;

                case LORENZO_TOKEN:
                    LorenzoTokenMessage l = (LorenzoTokenMessage) message;
                    viewUpdater.execute(() -> view.printLorenzoToken(l.getLorenzoTokenReduced()));
                    break;

                case FAITH_TRACK_MESSAGE:
                    FaithTrackMessage f = (FaithTrackMessage) message;
                    viewUpdater.execute(() -> view.printFaithTrack(f.getFaithTrack()));
                    break;

                case SETUP_RESOURCES:
                    SetupResourcesRequest resourcesRequest = (SetupResourcesRequest) message;
                    viewUpdater.execute(() ->
                    {
                        try {
                            view.askSetupResource(resourcesRequest.getNumberOfResources());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
                    break;

                case FINAL_PRODUCTION:
                    ChosenProductionMessage finalProduction = (ChosenProductionMessage) message;
                    viewUpdater.execute(() -> view.printFinalProduction(finalProduction.getInput(), finalProduction.getOutput()));
                    break;
                case AVAILABLE_PRODUCTION_CARDS:
                    AvailableCardsMessage a = (AvailableCardsMessage) message;
                    viewUpdater.execute(() -> view.printAvailableCards(a.getReducedAvailableCards()));
                    break;

                case AVAILABLE_LEADERS:
                    LeaderCardMessage leaderCardMessage = (LeaderCardMessage) message;
                    List<LeaderCard> built = deserializeLeaderCards(leaderCardMessage);
                    viewUpdater.execute(() ->
                            view.showLeaderCards(built));
                    break;

                case SETUP_LEADERS:
                    viewUpdater.execute(() ->
                    {
                        try {
                            view.askToDiscard();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
                    break;

                case WIN_MESSAGE:
                    WinMessage winMessage = (WinMessage) message;
                    viewUpdater.execute(() -> view.showWinMatch(winMessage.getMessage()));
                    break;

                case YOUR_TURN:
                    YourTurnMessage yourTurnMessage = (YourTurnMessage) message;
                    viewUpdater.execute(() -> view.currentTurn(yourTurnMessage.getMessage()));
                    break;

                case ERROR:
                    ErrorMessage e = (ErrorMessage) message;
                    viewUpdater.execute(() -> view.showError(e.getErrorMessage()));
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Method to deserialize leader cards.
     * @param message: message with information
     */
    public List<LeaderCard> deserializeLeaderCards(LeaderCardMessage message){
        List<LeaderCard> deserialized = new ArrayList<>();

        if(message.getSize()!=0){

            for (int i = 0; i < message.getSize(); i++) {

                switch (message.getEffects().get(i)){

                    case DISCOUNT:
                        DevelopmentTag[] discount = new DevelopmentTag[message.getOthers().get(i).size()];
                        for (int j = 0; j < message.getOthers().get(i).size(); j++) {
                            discount[j] = new DevelopmentTag(1,message.getOthers().get(i).get(j), Level.ANY);
                        }
                        deserialized.add(new DiscountLeaderCard
                                (2,
                                        EffectType.DISCOUNT,
                                        discount,
                                        message.getResources().get(i)));
                        break;

                    case EXTRA_INVENTORY:
                        ResourceTag[] inventory = new ResourceTag[] {new ResourceTag(message.getStorage().get(i), 5)};
                        deserialized.add(new ExtraInventoryLeaderCard
                                (3,
                                        EffectType.EXTRA_INVENTORY,
                                        inventory,
                                        null,
                                        message.getResources().get(i)));
                        break;

                    case MARBLE_EXCHANGE:
                        DevelopmentTag[] exchange = new DevelopmentTag[message.getOthers().get(i).size()];
                        for (int j = 0; j < message.getOthers().get(i).size(); j++) {
                            if(j==0)
                                exchange[j] = new DevelopmentTag(2,message.getOthers().get(i).get(j), Level.ANY);
                            else
                                exchange[j] = new DevelopmentTag(1,message.getOthers().get(i).get(j), Level.ANY);
                        }
                        deserialized.add(new MarbleExchangeLeaderCard
                                (5,
                                        EffectType.MARBLE_EXCHANGE,
                                        exchange,
                                        message.getResources().get(i)));
                        break;

                    case EXTRA_PRODUCTION:
                        DevelopmentTag[] production = new DevelopmentTag[message.getOthers().get(i).size()];
                        for (int j = 0; j < message.getOthers().get(i).size(); j++) {
                            production[j] = new DevelopmentTag(1,message.getOthers().get(i).get(j), Level.TWO);
                        }
                        deserialized.add(new ExtraProductionLeaderCard
                                (message.getResources().get(i),
                                        4,
                                        EffectType.EXTRA_PRODUCTION,
                                        production,
                                        new ResourceTag[] {new ResourceTag(message.getResources().get(i), 1)},
                                        new ResourceTag[] {new ResourceTag(ResourceType.UNDEFINED, 1),
                                                new ResourceTag(ResourceType.FAITH, 1)}));
                        break;
                }
            }
        }
        return deserialized;
    }


    /**
     * Sends a connection request to the server's IP and port.
     *
     * @param port      : port to connect.
     * @param ipAddress : address of the server.
     */
    @Override
    public void onServerInfoUpdate(int port, String ipAddress) {
        //Not used in offline games.
    }

    /**
     * Method to send a NicknameRequest message to the controller which will be
     * accepted since is the only player available.
     * {@link NicknameRequest}
     *
     * @param nickname: nickname to add to the
     */
    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        VirtualView vv = new VirtualView(null);
        gameManager.getLobbyManager().addNewPlayer(nickname, vv);
    }

    /**
     * Method to send an update on the lobby size.
     *
     * @param gameSize : number of players expected.
     */
    @Override
    public void onUpdateNumberOfPlayers(int gameSize) {
        gameManager.onMessage(new OneIntMessage(nickname, PossibleMessages.GAME_SIZE, gameSize));
    }

    /**
     * Method to provide the server of 1 resource the client would like to start with.
     * The number of setup resources depends on the playing order set by the Lobby Manager.
     * {@link ILobbyManager}
     *
     * @param r1 : Resource chosen.
     */
    @Override
    public void onUpdateSetupResource(LinkedList<ResourceType> r1) {

    }

    /**
     * Method to provide the server the index of the 2 leader cards the client would like to discard.
     *
     * @param l1 : first leader card.
     * @param l2 : second leader card.
     */
    @Override
    public void onUpdateSetupLeaders(int l1, int l2) {

    }

    /**
     * Message to deposit a specific resource to the strongbox.
     */
    @Override
    public void onUpdateSourceStrongBox() {

    }

    /**
     * Message to deposit a specific resource to the warehouse.
     */
    @Override
    public void onUpdateSourceWarehouse() {

    }

    /**
     * Method to send a BaseProduction request, with specific input/output resources.
     *
     * @param i1 : input resource one.
     * @param i2 : input resource two.
     * @param o1 : output resource one.
     */
    @Override
    public void onUpdateBaseActivation(ResourceType i1, ResourceType i2, ResourceType o1) {

    }

    /**
     * Sends a message with the index of the production card to activate.
     *
     * @param c1 : index of the card.
     */
    @Override
    public void onUpdateActivateProductionCard(int c1) {

    }

    /**
     * Sends a message to the server with the index of the leader to discard.
     *
     * @param l1 : index of the leader to discard.
     */
    @Override
    public void onUpdateDiscardLeader(int l1) {

    }

    /**
     * Sends a message to the server with the index of the leader to activate.
     *
     * @param l1 : index of the leader to activate.
     */
    @Override
    public void onUpdateActivateLeader(int l1) {

    }

    /**
     * Sends a message to activate an extra-inventory slot.
     *
     * @param e1 : index of the slot to activate.
     * @param r1 : preferred output resource.
     */
    @Override
    public void onUpdateActivateExtraProduction(int e1, ResourceType r1) {

    }

    /**
     * Sends a message to get resources from a specific arrow from market.
     *
     * @param r1 : row or column chosen.
     */
    @Override
    public void onUpdateGetResources(int r1) {

    }

    /**
     * Sends a message containing the index of the Production Card to buy. The card is selected from the
     * available ones. {@link ResourceMarket}
     *
     * @param card : Index of the card to buy.
     * @param slot : production slot destination.
     */
    @Override
    public void onUpdateBuyCard(int card, int slot) {

    }

    /**
     * Sends a request to swap two shelves in the warehouse.
     *
     * @param shelf1
     * @param shelf2
     */
    @Override
    public void onUpdateSwap(int shelf1, int shelf2) {

    }

    /**
     * Sends a request to check the state of another player.
     *
     * @param enemyName : player to check.
     */
    @Override
    public void onUpdatePeek(String enemyName) {

    }

    /**
     * Sends a message containing the resource to get in exchange for a white marble.
     *
     * @param r1 : resource to exchange.
     */
    @Override
    public void onUpdateExchangeResource(ResourceType r1) {

    }

    @Override
    public void onUpdateDeposit(int index) {

    }

    @Override
    public void onUpdateEndTurn() {

    }

    @Override
    public void onUpdateExecuteProduction() {
        
    }
}
