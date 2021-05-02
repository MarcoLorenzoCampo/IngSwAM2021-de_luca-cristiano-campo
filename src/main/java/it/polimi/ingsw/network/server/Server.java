package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.network.messages.Message;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Server {

    private final GameManager gameManager;
    private final Map<String, ClientHandler> clientHandlerMap;
    private final Object lock;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Server(GameManager gameManager) {
        this.gameManager = gameManager;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
    }

    public void addNewClient(String nickname, ClientHandler clientHandler) {

    }

    public void onMessage(Message message) {

    }

    public void onDisconnection(ClientHandler clientHandler) {

    }
}
