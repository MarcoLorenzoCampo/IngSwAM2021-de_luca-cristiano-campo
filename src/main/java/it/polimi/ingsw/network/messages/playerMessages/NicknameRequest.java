package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;
import it.polimi.ingsw.network.messages.Message;

public class NicknameRequest extends Message {

    private static final long serialVersionUID = -2692433087958602088L;

    public NicknameRequest(String username){
        super.setMessageType(PossiblePlayerMessages.SEND_NICKNAME);
        super.setSenderUsername(username);
    }
}
