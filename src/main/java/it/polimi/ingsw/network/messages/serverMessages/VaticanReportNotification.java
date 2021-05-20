package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.network.messages.Message;

/**
 * Message sent by the server as a notification to the controller.
 * Controller starts the vatican report session.
 */
public class VaticanReportNotification extends Message {
    private static final long serialVersionUID = -2291302082109808634L;

    private final int popeTileIndex;
    private final int range;

    public VaticanReportNotification(int popeTileIndex, int range) {
        super.setMessageType(PossibleMessages.VATICAN_REPORT_NOTIFICATION);
        super.setSenderUsername("SERVER_MESSAGE");

        this.popeTileIndex = popeTileIndex;
        this.range = range;
    }

    public int getPopeTileIndex() {
        return popeTileIndex;
    }

    public int getRange() {
        return range;
    }
}
