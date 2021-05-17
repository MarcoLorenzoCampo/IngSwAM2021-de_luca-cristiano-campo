package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.ILobbyManager;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.ResourceMarket;
import it.polimi.ingsw.network.eventHandlers.Observer;
import it.polimi.ingsw.network.eventHandlers.ViewObserver;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.NicknameRequest;
import it.polimi.ingsw.network.views.IView;

import java.util.LinkedList;
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
    private GameManager gameManager;

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
        this.view = view;
        viewUpdater = Executors.newSingleThreadExecutor();
    }

    @Override
    public void update(Message message) {

    }

    /**
     * Sends a connection request to the server's IP and port.
     *
     * @param port      : port to connect.
     * @param ipAddress : address of the server.
     */
    @Override
    public void onServerInfoUpdate(int port, String ipAddress) {

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

    }

    /**
     * Method to send an update on the lobby size.
     *
     * @param gameSize : number of players expected.
     */
    @Override
    public void onUpdateNumberOfPlayers(int gameSize) {

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
}
