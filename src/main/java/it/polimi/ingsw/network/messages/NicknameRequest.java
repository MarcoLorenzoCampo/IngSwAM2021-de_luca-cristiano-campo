package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.enumerations.PossibleMessages;

public class NicknameRequest extends Message{

    public NicknameRequest(String username){
        super.setMessageType(PossibleMessages.SEND_NICKNAME);
        super.setSenderUsername(username);
    }
}
