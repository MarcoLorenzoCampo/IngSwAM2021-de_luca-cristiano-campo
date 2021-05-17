package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.model.market.leaderCards.*;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.eventHandlers.Observer;
import it.polimi.ingsw.network.eventHandlers.ViewObserver;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.playerMessages.*;

import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.network.views.IView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class to handle messages sent by the client. Acts in between network and generic view.
 */
public class OnlineClientManager implements ViewObserver, Observer {

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

    /**
     *
     */
    private final Executor viewUpdater;

    /**
     * Constructor of the client controller.
     * @param view: cli or gui.
     */
    public OnlineClientManager(IView view) {
        this.view = view;
        viewUpdater = Executors.newSingleThreadExecutor();
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

        if(message != null)

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

            case LORENZO_TOKEN:
                LorenzoTokenMessage l = (LorenzoTokenMessage) message;
                viewUpdater.execute(() -> view.printLorenzoToken(l.getLorenzoToken()));
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

            case AVAILABLE_LEADERS:
                LeaderCardMessage leaderCardMessage = (LeaderCardMessage) message;
                List<LeaderCard> built = deserializeLeaderCards(leaderCardMessage);
                viewUpdater.execute(() ->
                        view.showLeaderCards(built));
                break;

            case SETUP_LEADERS:
                SetupLeaderRequest setupLeaderRequest = (SetupLeaderRequest) message;
                viewUpdater.execute(() ->
                {
                   try{
                       view.askToDiscard();
                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   }
                });

            default: break;
        }
    }

    /**
     * Method to deserialize leader cards
     * @param message: message with infomation
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


    @Override
    public void onServerInfoUpdate(int port, String ipAddress) {

        try {
            client = new Client(port, ipAddress);
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
    public void onUpdateExchangeResource(ResourceType r1) {
        client.sendMessage(new ExchangeResourceMessage(nickname, r1));
    }
}
