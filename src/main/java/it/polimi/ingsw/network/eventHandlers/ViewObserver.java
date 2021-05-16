package it.polimi.ingsw.network.eventHandlers;

import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.playerMessages.NicknameRequest;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to observe the view. On information updates calls specific routines.
 */
public interface ViewObserver {

    /**
     * Sends a connection request to the server's IP and port.
     * @param port: port to connect.
     * @param ipAddress: address of the server.
     */
    void onServerInfoUpdate(int port, String ipAddress);

    /**
     * Method to send a NicknameRequest message to the server which will eventually
     * be validated or refused.
     * {@link NicknameRequest}
     */
    void onUpdateNickname(String nickname);

    /**
     * Method to send an update on the lobby size.
     * @param gameSize: number of players expected.
     */
    void onUpdateNumberOfPlayers(int gameSize);

    /**
     * Method to provide the server of 1 resource the client would like to start with.
     * The number of setup resources depends on the playing order set by the Lobby Manager.
     * {@link it.polimi.ingsw.controller.ILobbyManager}
     *
     * @param r1: Resource chosen.
     */
    void onUpdateSetupResource(LinkedList<ResourceType> r1);

    /**
     * Method to provide the server the index of the 2 leader cards the client would like to discard.
     * @param l1: first leader card.
     * @param l2: second leader card.
     */
    void onUpdateSetupLeaders(int l1, int l2);

    /**
     * Message to deposit a specific resource to the strongbox.
     */
    void onUpdateSourceStrongBox();

    /**
     * Message to deposit a specific resource to the warehouse.
     */
    void onUpdateSourceWarehouse();

    /**
     * Method to send a BaseProduction request, with specific input/output resources.
     * @param i1: input resource one.
     * @param i2: input resource two.
     * @param o1: output resource one.
     */
    void onUpdateBaseActivation(ResourceType i1, ResourceType i2, ResourceType o1);

    /**
     * Sends a message with the index of the production card to activate.
     * @param c1: index of the card.
     */
    void onUpdateActivateProductionCard(int c1);

    /**
     * Sends a message to the server with the index of the leader to discard.
     * @param l1: index of the leader to discard.
     */
    void onUpdateDiscardLeader(int l1);

    /**
     * Sends a message to the server with the index of the leader to activate.
     * @param l1: index of the leader to activate.
     */
    void onUpdateActivateLeader(int l1);

    /**
     * Sends a message to activate an extra-inventory slot.
     * @param e1: index of the slot to activate.
     * @param r1: preferred output resource.
     */
    void onUpdateActivateExtraProduction(int e1, ResourceType r1);

    /**
     * Sends a message to get resources from a specific arrow from market.
     * @param r1: row or column chosen.
     */
    void onUpdateGetResources(int r1);

    /**
     * Sends a message containing the index of the Production Card to buy. The card is selected from the
     * available ones. {@link it.polimi.ingsw.model.market.ResourceMarket}
     *
     * @param card: Index of the card to buy.
     * @param slot: production slot destination.
     */
    void onUpdateBuyCard(int card, int slot);

    /**
     * Sends a request to swap two shelves in the warehouse.
     */
    void onUpdateSwap(int shelf1, int shelf2);

    /**
     * Sends a request to check the state of another player.
     * @param enemyName: player to check.
     */
    void onUpdatePeek(String enemyName);

    /**
     * Sends a message containing the resource to get in exchange for a white marble.
     * @param r1: resource to exchange.
     */
    void onUpdateExchangeResource(ResourceType r1);
}
