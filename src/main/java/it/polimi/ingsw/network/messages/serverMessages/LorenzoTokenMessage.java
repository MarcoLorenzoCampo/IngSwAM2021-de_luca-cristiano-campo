package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.token.IToken;
import it.polimi.ingsw.network.messages.Message;

/**
 * Contains information about the Token Lorenzo has just activated. Shown in single player matches after
 * each turn.
 */
public class LorenzoTokenMessage extends Message {

    private static final long serialVersionUID = 4057993691377248426L;

    private final IToken lorenzoToken;

    public LorenzoTokenMessage(IToken lorenzoToken) {

        super.setMessageType(PossibleMessages.LORENZO_TOKEN);
        this.lorenzoToken = lorenzoToken;
    }

    public IToken getLorenzoToken() {
        return lorenzoToken;
    }
}
