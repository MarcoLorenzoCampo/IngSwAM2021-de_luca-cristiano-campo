package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

import java.io.Serializable;

/**
 * Message sent by the server as a reply of the connection outcome.
 */
public class LoginOutcomeMessage extends Message implements Serializable {

    private static final long serialVersionUID = -5912553119888256502L;

    private final boolean connectionOutcome;
    private final boolean nicknameAccepted;
    private final boolean reconnected;

    public LoginOutcomeMessage(boolean connectionOutcome, boolean nicknameAccepted, boolean reconnected) {
        super.setMessageType(PossibleMessages.LOGIN_OUTCOME);

        this.connectionOutcome = connectionOutcome;
        this.nicknameAccepted = nicknameAccepted;
        this.reconnected = reconnected;
    }

    public boolean isReconnected() {
        return reconnected;
    }

    public boolean isConnectionOutcome() {
        return connectionOutcome;
    }

    public boolean isNicknameAccepted() {
        return nicknameAccepted;
    }
}
