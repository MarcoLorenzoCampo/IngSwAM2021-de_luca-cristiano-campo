package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.views.IView;

/**
 * Class to handle messages sent by the client.
 */
public class ClientController {

    /**
     * Reference to the generic view chosen.
     */
    private IView view;

    /**
     * Client who's being handled.
     */
    private Client client;

    /**
     * Name of the client.
     */
    private String nickname;

    public ClientController() {

    }
}
