package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossiblePlayerMessages;

public class NicknameRequest extends Message{

    public NicknameRequest(String username){
        super.setMessageType(PossiblePlayerMessages.SEND_NICKNAME);
        super.setSenderUsername(username);
    }
}
