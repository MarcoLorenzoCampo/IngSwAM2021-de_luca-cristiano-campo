package it.polimi.ingsw.network.virtualView;

import it.polimi.ingsw.network.server.ClientHandler;

import java.util.Observable;
import java.util.Observer;

public class VirtualView implements IView {

    private ClientHandler thisClient;

    public VirtualView(ClientHandler thisClient) {
        this.thisClient = thisClient;
    }

}
