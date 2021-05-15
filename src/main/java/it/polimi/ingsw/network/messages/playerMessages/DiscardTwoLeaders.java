package it.polimi.ingsw.network.messages.playerMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

public class DiscardTwoLeaders extends Message {
    private static final long serialVersionUID = 1384879696476545809L;
    private int L1;
    private int L2;


    public DiscardTwoLeaders(String nickname, int first, int second){
        super.setMessageType(PossibleMessages.SETUP_LEADERS);
        super.setSenderUsername(nickname);
        L1 = first;
        L2 = second;
    }

    public int getL1() {
        return L1;
    }

    public int getL2() {
        return L2;
    }
}
