package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.Message;

public interface IClientHandler {

    boolean isConnected();
    void disconnect();
    void sendMessage(Message message);
}
