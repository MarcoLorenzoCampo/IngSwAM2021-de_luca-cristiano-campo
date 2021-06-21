package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.eventHandlers.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.playerMessages.NicknameRequest;
import it.polimi.ingsw.network.messages.playerMessages.OneIntMessage;
import it.polimi.ingsw.network.messages.serverMessages.LobbySizeReply;
import it.polimi.ingsw.network.messages.serverMessages.LobbySizeRequest;
import it.polimi.ingsw.network.server.IClientHandler;

public class LocalStream implements IClientHandler {
    private GameManager gameManager;
    private Client client;

    public LocalStream(){
        gameManager = new GameManager();
        gameManager.setLobbyManager("singlePlayer");
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void sendMessage(Message message) {
        client.forwardMessage(message);
    }


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

    }
    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public void disconnect() {

    }
}
