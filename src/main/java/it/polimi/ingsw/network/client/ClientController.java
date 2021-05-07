package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.NicknameRequest;
import it.polimi.ingsw.network.messages.OneIntMessage;
import it.polimi.ingsw.network.views.IView;

/**
 * Class to handle messages sent by the client. Acts in between network and generic view.
 */
public class ClientController {

    /**
     * Reference to the generic view chosen.
     */
    private IView view;

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
    public ClientController(IView view) {
        this.view = view;
    }

    /**
     * Method to send a NicknameRequest message to the server which will eventually
     * be validated or refused.
     * {@link NicknameRequest}
     */
    public void onNicknameUpdate(String nickname) {
        this.nickname = nickname;
        iClient.sendMessage(new NicknameRequest(nickname));
    }

    /**
     * Method to send an update on the lobby size.
     * @param gameSize: number of players expected.
     */
    public void onNumberOfPlayersUpdate(int gameSize) {
        iClient.sendMessage(new OneIntMessage(nickname, PossibleMessages.GAME_SIZE, gameSize));
    }
}
