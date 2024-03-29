package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.*;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.eventHandlers.Observer;
import it.polimi.ingsw.eventHandlers.ViewObserver;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.playerMessages.*;

import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.views.IView;
import it.polimi.ingsw.parsers.ProductionCardsParser;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class to handle messages sent by the client. Acts in between network and generic view.
 */
public class ClientManager implements ViewObserver, Observer {

    /**
     * Reference to the generic view chosen.
     */
    private final IView view;

    /**
     * Client who's being handled.
     */
    private Client client;

    /**
     * Name of the client.
     */
    private String nickname;

    private final Executor viewUpdater;

    private final List<ProductionCard> allProductionCards;


    /**
     * Constructor of the client manager.
     * @param view: cli or gui
     * @param isOffline: game mode
     */
    public ClientManager(IView view, boolean isOffline) {
        this.view = view;
        this.allProductionCards = ProductionCardsParser.parseProductionDeck();
        viewUpdater = Executors.newSingleThreadExecutor();
        if(isOffline){
            client = new Client(new LocalStream(), view);
            client.addObserver(this);
        }
    }

    /**
     * Takes action based on the message type of the message received from the server.
     * Each server response will be displayed in the CLI/GUI.
     *
     * See {@link it.polimi.ingsw.enumerations.PossibleMessages} for a full list of
     * available server messages.
     *
     * @param message the message received from the server.
     */
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
                            view.printLeaders(leader_prod));
                    viewUpdater.execute(() -> view.printProductionBoard(deserializeProductionBoard(productionBoard.getProductions())));
                    break;

                case WAREHOUSE:
                    WarehouseMessage warehouse = (WarehouseMessage) message;
                    viewUpdater.execute(() -> view.printWarehouse(warehouse.getWarehouse(), warehouse.getExtra_shelf()));
                    break;

                case LORENZO_TOKEN:
                    LorenzoTokenMessage l = (LorenzoTokenMessage) message;
                    viewUpdater.execute(() -> view.printLorenzoToken(l.getLorenzoTokenReduced(), l.getColor(), l.getQuantity()));
                    break;

                case LORENZO_FAITHTRACK:
                    LorenzoFaithTrackMessage lorenzoFaithTrack = (LorenzoFaithTrackMessage) message;
                    viewUpdater.execute(()-> view.printLorenzoFaithTrack(lorenzoFaithTrack.getPosition()));
                    break;

                case FAITH_TRACK_MESSAGE:
                    FaithTrackMessage f = (FaithTrackMessage) message;
                    viewUpdater.execute(() -> view.printFaithTrack(f.getFaithTrack()));
                    break;

                case POPE_FAVOR:
                    TwoIntMessage popefavor = (TwoIntMessage) message;
                    viewUpdater.execute(()-> view.printPopeFavor(popefavor.getFirstNumber(), popefavor.getSecondNumber()));
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
                        }
                    );
                    break;

                case FINAL_PRODUCTION:
                    ChosenProductionMessage finalProduction = (ChosenProductionMessage) message;
                    viewUpdater.execute(() -> view.printFinalProduction(finalProduction.getInput(), finalProduction.getOutput()));
                    break;

                case AVAILABLE_PRODUCTION_CARDS:
                    AvailableCardsMessage a = (AvailableCardsMessage) message;
                    ArrayList<ProductionCard> deserializedProductions = deserializeProductionCards(a.getAvailableID());
                    viewUpdater.execute(() -> view.printAvailableCards(deserializedProductions));
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

                case PEEK_MESSAGE:
                    PeekUpdateMessage p = (PeekUpdateMessage) message;
                    viewUpdater.execute(() -> view.getPeek(
                            p.getName(),
                            p.getFaithPosition(),
                            p.getInventory(),
                            p.getCards(),
                            p.getResourceTypes()
                    ));
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Method to create the production board from the information sent
     * @param productionSlots: map that holds the index of the slot and the id of the production card held
     * @return: map that holds the index of the slot and the concrete production card held
     */
    private HashMap<Integer, ProductionCard> deserializeProductionBoard(HashMap<Integer, Integer> productionSlots){
        HashMap<Integer, ProductionCard> deserialized = new HashMap<>();
        for (Map.Entry<Integer,Integer> iterator: productionSlots.entrySet()) {
            if(iterator.getValue()==-1){
                deserialized.put(iterator.getKey(), null);
            } else {
                deserialized.put(iterator.getKey(), allProductionCards.get(iterator.getValue()));
            }
        }
        return  deserialized;
    }

    /**
     * Method that creates the concrete production cards from the IDs sent
     * @param availableID: list of the IDs of the available cards
     * @return: list of concrete cards associated with said IDs
     */
    private ArrayList<ProductionCard> deserializeProductionCards(ArrayList<Integer> availableID) {
        ArrayList<ProductionCard> available = new ArrayList<>();
        for (Integer iterator: availableID) {
            available.add(allProductionCards.get(iterator));
        }
        return available;
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
                deserialized.get(i).deserializeActive(message.getActive().get(i));
            }
        }
        return deserialized;
    }

    @Override
    public void onServerInfoUpdate(int port, String ipAddress) {

        try {
            client = new Client(port, ipAddress, view);
            client.addObserver(this);
            //client.startPing(true);
            client.readMessage();
            viewUpdater.execute(view::askNickname);
        } catch (IOException e) {
            viewUpdater.execute(() -> view.showLoginOutput(false, false, false));
        }
    }

    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new NicknameRequest(this.nickname));
    }

    @Override
    public void onUpdateNumberOfPlayers(int gameSize) {
        client.sendMessage(new OneIntMessage(nickname, PossibleMessages.GAME_SIZE, gameSize));
    }

    @Override
    public void onUpdateSetupResource(LinkedList<ResourceType> r1) {
        client.sendMessage(new SetupResourceAnswer(nickname, r1.size(), r1));
    }

    @Override
    public void onUpdateSetupLeaders(int l1, int l2) {
        client.sendMessage(
                new DiscardTwoLeaders(nickname, l1, l2)
        );
    }

    @Override
    public void onUpdateSourceStrongBox() {
        client.sendMessage(new SourceStrongboxMessage(nickname));
    }

    @Override
    public void onUpdateSourceWarehouse() {
        client.sendMessage(new SourceWarehouseMessage(nickname));
    }

    @Override
    public void onUpdateBaseActivation(ResourceType i1, ResourceType i2, ResourceType o1) {
        client.sendMessage(new BaseProductionMessage(nickname, i1, i2, o1));
    }

    @Override
    public void onUpdateActivateProductionCard(int c1) {
        client.sendMessage(new OneIntMessage(nickname, PossibleMessages.ACTIVATE_PRODUCTION, c1));
    }

    @Override
    public void onUpdateDiscardLeader(int l1) {
        client.sendMessage(new OneIntMessage(nickname, PossibleMessages.DISCARD_LEADER, l1));
    }

    @Override
    public void onUpdateActivateLeader(int l1) {
        client.sendMessage(new OneIntMessage(nickname, PossibleMessages.ACTIVATE_LEADER, l1));
    }

    @Override
    public void onUpdateActivateExtraProduction(int e1, ResourceType r1) {
        client.sendMessage(new ExtraProductionMessage(nickname, e1, r1));
    }

    @Override
    public void onUpdateGetResources(int r1) {
        client.sendMessage(new OneIntMessage(nickname, PossibleMessages.GET_RESOURCES, r1));
    }

    @Override
    public void onUpdateBuyCard(int card, int slot) {
        client.sendMessage(new TwoIntMessage(nickname, PossibleMessages.BUY_PRODUCTION, card, slot));
    }

    @Override
    public void onUpdateSwap(int shelf1, int shelf2) {
        client.sendMessage(new TwoIntMessage(nickname, PossibleMessages.SWAP ,shelf1, shelf2));
    }

    @Override
    public void onUpdatePeek(String enemyName) {
        client.sendMessage(new PeekMessage(nickname, enemyName));
    }

    @Override
    public void onUpdateExchangeResource(ResourceType r1, int place) {
        client.sendMessage(new ExchangeResourceMessage(nickname, r1, place));
    }

    @Override
    public void onUpdateDeposit(int index) {
        client.sendMessage(new OneIntMessage(nickname,PossibleMessages.DEPOSIT, index));
    }

    @Override
    public void onUpdateEndTurn() {
        client.sendMessage(new EndTurnMessage(nickname));
    }

    @Override
    public void onUpdateExecuteProduction() {
        client.sendMessage(new ExecuteProductionMessage(nickname));
    }
}
