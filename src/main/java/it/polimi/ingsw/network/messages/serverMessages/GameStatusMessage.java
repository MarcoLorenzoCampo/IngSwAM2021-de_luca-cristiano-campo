package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the basic information regarding the game status: the current active players, and
 * how many turns have passed since the beginning of the game.
 */
public class GameStatusMessage extends Message {

    private static final long serialVersionUID = 5377816352835385508L;

    private final List<String> activePlayers;
    private final int numberOfTurns;

    public GameStatusMessage(List<String> activePlayers, int numberOfTurns) {

        super.setMessageType(PossibleMessages.GAME_STATUS);

        this.activePlayers = activePlayers;
        this.numberOfTurns = numberOfTurns;
    }

    public List<String> getActivePlayers() {
        return activePlayers;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }
}
