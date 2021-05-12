package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class NicknameRequest extends Message {

    private static final long serialVersionUID = -2692433087958602088L;

    public NicknameRequest(String username){
        super.setMessageType(PossibleMessages.SEND_NICKNAME);
        super.setSenderUsername(username);
    }
}
