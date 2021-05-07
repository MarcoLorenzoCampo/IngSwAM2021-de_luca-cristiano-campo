package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.views.IView;

/**
 * Class to handle messages sent by the client. Acts in between network and generic view.
 */
public class ClientInterpreter {

    /**
     * Reference to the generic view chosen.
     */
    private final IView view;

    /**
     * Client who's being handled.
     */
    private IClient iClient;

    /**
     * Name of the client.
     */
    private String nickname;

    /**
     * Constructor of the client controller.
     * @param view: cli or gui.
     */
    public ClientInterpreter(IView view) {
        this.view = view;
    }

    /**
     * Method to send a NicknameRequest message to the server which will eventually
     * be validated or refused.
     * {@link NicknameRequest}
     */
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        iClient.sendMessage(new NicknameRequest(nickname));
    }

    /**
     * Method to send an update on the lobby size.
     * @param gameSize: number of players expected.
     */
    public void onUpdateNumberOfPlayers(int gameSize) {
        iClient.sendMessage(new OneIntMessage(nickname, PossibleMessages.GAME_SIZE, gameSize));
    }

    /**
     * Method to provide the server of 1 resource the client would like to start with.
     * The number of setup resources depends on the playing order set by the Lobby Manager.
     * {@link it.polimi.ingsw.controller.ILobbyManager}
     *
     * @param r1: Resource chosen.
     */
    public void onUpdateSetupResource(ResourceType r1) {
        iClient.sendMessage(new ChosenResourceMessage(nickname, r1));
    }

    /**
     * Method to provide the server the index of the 2 leader cards the client would like to discard.
     * @param l1: first leader card.
     * @param l2: second leader card.
     */
    public void onUpdateSetupLeaders(int l1, int l2) {
        iClient.sendMessage(
                new TwoIntMessage(nickname, PossibleMessages.SETUP_LEADERS, l1, l2)
        );
    }

    /**
     * Message to deposit a specific resource to the strongbox.
     */
    public void onUpdateSourceStrongBox() {
        iClient.sendMessage(new SourceStrongboxMessage(nickname));
    }

    /**
     * Method to send a BaseProduction request, with specific input/output resources.
     * @param i1: input resource one.
     * @param i2: input resource two.
     * @param o1: output resource one.
     */
    public void onUpdateBaseActivation(ResourceType i1, ResourceType i2, ResourceType o1) {
        iClient.sendMessage(new BaseProductionMessage(nickname, i1, i2, o1));
    }

    /**
     * Sends a message to the server with the index of the leader to discard.
     * @param l1: index of the leader to discard.
     */
    public void onUpdateDiscardLeader(int l1) {
        iClient.sendMessage(new OneIntMessage(nickname, PossibleMessages.DISCARD_LEADER, l1));
    }

    /**
     * Sends a message to the server with the index of the leader to activate.
     * @param l1: index of the leader to activate.
     */
    public void onUpdateActivateLeader(int l1) {
        iClient.sendMessage(new OneIntMessage(nickname, PossibleMessages.ACTIVATE_LEADER, l1));
    }


    //-------------------------------------- ACTION REQUESTS -----------------------------------------

    /**
     * Sends a message to request an action. In this case
     * @param r1: row or column chosen.
     */
    public void onUpdateGetResources(int r1) {
        iClient.sendMessage(new OneIntMessage(nickname, PossibleMessages.GET_RESOURCES, r1));
    }

    /**
     * Sends a message containing the index of the Production Card to buy. The card is selected from the
     * available ones. {@link it.polimi.ingsw.model.market.ResourceMarket}
     *
     * @param c1: Index of the card to buy.
     */
    public void onUpdateByuCard(int c1) {
        iClient.sendMessage(new OneIntMessage(nickname, PossibleMessages.BUY_PRODUCTION, c1));
    }

    /**
     * Sends a request to swap two shelves in the warehouse.
     */
    public void onUpdateSwap() {

    }
}
