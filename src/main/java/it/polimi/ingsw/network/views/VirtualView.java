package it.polimi.ingsw.network.views;

import it.polimi.ingsw.network.eventHandlers.observers.Observer;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.IClientHandler;

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

    }

    @Override
    public void askPlayerNumber() {

    }

    @Override
    public void showLoginOutput(boolean connectionSuccess, boolean nicknameAccepted, boolean reconnected) {

    }

    @Override
    public void showGenericString(String genericMessage) {

    }

    @Override
    public void askReplacementResource() {

    }

    @Override
    public void askToDiscard() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void currentTurn(String message) {

    }

    @Override
    public void turnEnded(String message) {

    }

    @Override
    public void askSetupResource() {

    }

    @Override
    public void showMatchInfo() {

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

    public void disconnect() {

    }
}
