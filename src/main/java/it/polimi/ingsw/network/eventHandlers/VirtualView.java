package it.polimi.ingsw.network.eventHandlers;

import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.market.leaderCards.LeaderCard;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.IClientHandler;
import it.polimi.ingsw.network.views.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hides network classes and methods from the controller.The controller interacts woith the virtual view
 * as clients interact with a "real" view, calling methods from the IView interface {@link IView}.
 */
public class VirtualView implements IView, Observer {

    private final IClientHandler clientHandler;

    /**
     * Class constructor.
     * @param clientHandler: clientHandler {@link ClientHandler} each virtual view communicates
     *                     with. Messages are sent through the handler.
     */
    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * Default getter.
     * @return clientHandler associated with this VirtualView.
     */
    public IClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public void askNickname() {
        clientHandler.sendMessage(
                new LoginOutcomeMessage(true, false, false)
        );
    }

    @Override
    public void askPlayerNumber() {
        clientHandler.sendMessage(new LobbySizeRequest());
    }

    @Override
    public void showLoginOutput(boolean connectionSuccess, boolean nicknameAccepted, boolean reconnected) {
        clientHandler.sendMessage(new LoginOutcomeMessage(connectionSuccess, nicknameAccepted, reconnected));
    }

    @Override
    public void showGenericString(String genericMessage) {
        clientHandler.sendMessage(new GenericMessageFromServer(genericMessage));
    }

    @Override
    public void showInvalidAction(String errorMessage) {
        clientHandler.sendMessage(new GenericMessageFromServer(errorMessage));
    }
/*
    @Override
    public void askReplacementResource(ResourceType r1, ResourceType r2) {

    }

 */


    @Override
    public void askToDiscard() {
        clientHandler.sendMessage(new SetupLeaderRequest());
    }

    @Override
    public void showLeaderCards(List<LeaderCard> cards) {
        clientHandler.sendMessage(new LeaderCardMessage(cards));
    }

    @Override
    public void showError(String errorMessage) {
        clientHandler.sendMessage(new ErrorMessage(errorMessage));
    }

    @Override
    public void currentTurn(String message) {
        clientHandler.sendMessage(new YourTurnMessage(message));
    }

    @Override
    public void turnEnded(String message) {

    }

    @Override
    public void askSetupResource(int numberOfResources) {
        clientHandler.sendMessage(new SetupResourcesRequest(numberOfResources));
    }

    @Override
    public void showMatchInfo(List<String> playingNames) {
        clientHandler.sendMessage(new GameStatusMessage(playingNames));
    }

    @Override
    public void showWinMatch(String winner) {
        clientHandler.sendMessage(new WinMessage(winner));
    }

    @Override
    public void printResourceMarket(ResourceType[][] resourceMarket, ResourceType extraMarble) {
        clientHandler.sendMessage(new ResourceMarketMessage(resourceMarket, extraMarble));
    }

    /**
     * Method to show the available production cards.
     *
     * @param available : available production cards.
     */
    @Override
    public void printAvailableCards(List<ProductionCard> available) {
        clientHandler.sendMessage(new AvailableCardsMessage(available));
    }

    @Override
    public void printLorenzoToken(String lorenzoTokenReduced) {
        clientHandler.sendMessage(new LorenzoTokenMessage(lorenzoTokenReduced));
    }

    @Override
    public void printLeaders(List<LeaderCard> leaderCards) {
        clientHandler.sendMessage(new LeaderCardMessage(leaderCards));
    }

    @Override
    public void printBuffer(ArrayList<ResourceType> buffer) {

    }

    /**
     * Method to send a reduced String only version of an enemy status.
     *
     * @param name: name of the player whose info are being sent.
     * @param faithPosition: position on the faith track.
     * @param inventory: reduced inventory of owned resources.
     * @param cards: leader cards owned.
     */
    @Override
    public void getPeek(String name, int faithPosition, Map<ResourceType, Integer> inventory, List<EffectType> cards, List<ResourceType> leader_resource) {
        clientHandler.sendMessage(new PeekUpdateMessage(name, faithPosition, inventory, cards, leader_resource));
    }

    /**
     * Prints a reduced version of a player's inventory.
     *
     * @param inventory:
     */
    @Override
    public void printInventory(Map<ResourceType, Integer> inventory) {

    }

    @Override
    public void printStrongbox(Map<ResourceType, Integer> strongbox) {
        clientHandler.sendMessage(new StrongboxMessage(strongbox));
    }

    @Override
    public void printWarehouse(ArrayList<ResourceType> shelves, ArrayList<ResourceType> extras) {

    }

    @Override
    public void printProductionBoard(HashMap<Integer, ProductionCard> productionBoard) {

    }

    @Override
    public void printFinalProduction(HashMap<ResourceType, Integer> input, HashMap<ResourceType, Integer> output) {

    }

    /**
     * Receives an update message from the model and sends it over the network to the client.
     * The action is performed based on the message type and depends on the view implemented by the client.
     *
     * @param message: update message.
     */
    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }

    @Override
    public void printFaithTrack(FaithTrack faithTrack) {
        clientHandler.sendMessage(new FaithTrackMessage(faithTrack));
    }
}
