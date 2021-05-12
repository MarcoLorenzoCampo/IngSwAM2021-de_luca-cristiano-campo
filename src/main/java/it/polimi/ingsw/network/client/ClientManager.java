package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.eventHandlers.observers.Observer;
import it.polimi.ingsw.network.eventHandlers.viewObservers.ViewObserver;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.playerMessages.*;
import it.polimi.ingsw.network.eventHandlers.IView;

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

    /**
     *
     */
    private final Executor viewUpdater;

    /**
     * Constructor of the client controller.
     * @param view: cli or gui.
     */
    public ClientManager(IView view) {
        this.view = view;
        viewUpdater = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onServerInfoUpdate(int port, String ipAddress) {

            client = new Client(port, ipAddress);
            client.addObserver(this);

            //Sets an asynchronous thread to read messages from server.
            client.readMessage();
            viewUpdater.execute(view::askNickname);
    }

    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new NicknameRequest(nickname));
    }

    @Override
    public void onUpdateNumberOfPlayers(int gameSize) {
        client.sendMessage(new OneIntMessage(nickname, PossibleMessages.GAME_SIZE, gameSize));
    }

    @Override
    public void onUpdateSetupResource(ResourceType r1) {
        client.sendMessage(new ChosenResourceMessage(nickname, r1));
    }

    @Override
    public void onUpdateSetupLeaders(int l1, int l2) {
        client.sendMessage(
                new TwoIntMessage(nickname, PossibleMessages.SETUP_LEADERS, l1, l2)
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

        switch (message.getMessageType()) {


            default: break;
        }
    }
}
