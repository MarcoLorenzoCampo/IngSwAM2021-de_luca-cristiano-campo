package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.OneIntMessage;
import it.polimi.ingsw.network.server.IClientHandler;

/**
 * Offline stream communication between Client and Controller.
 */
public class LocalStream implements IClientHandler {
    private final GameManager gameManager;
    private Client client;

    public LocalStream(){
        gameManager = new GameManager();
        gameManager.setLobbyManager("singlePlayer");
    }

    public void setClient(Client client) {
        this.client = client;
    }


    /**
     * takes message from game manager and sends it to the client
     * @param message: data sent from the game manager
     */
    @Override
    public void sendMessage(Message message) {
        client.forwardMessage(message);
    }

    /**
     * takes message from the client and sends it to the game manager.
     * Executes some interactions that the player doesn't have to do during a local game
     * @param message: data sent from the client
     */
    public void handleMessage(Message message){
        if(message.getMessageType().equals(PossibleMessages.SEND_NICKNAME)){
            gameManager.onMessage(message);
            gameManager.addVirtualView(message.getSenderUsername(), new VirtualView(this));
            gameManager.onMessage(new OneIntMessage(message.getSenderUsername(), PossibleMessages.GAME_SIZE, 1));
        }
        gameManager.onMessage(message);
    }

    @Override
    public void sameNameDisconnect() {
        //UNUSED
    }

    @Override
    public void disconnect() {
        //UNUSED
    }
}
