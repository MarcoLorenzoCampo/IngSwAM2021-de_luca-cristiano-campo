package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.faithtrack.FaithTrack;
import it.polimi.ingsw.network.messages.Message;

/**
 * Faith track update message.
 */
public class FaithTrackMessage extends Message {
    private static final long serialVersionUID = 7905200382931619217L;

    private final FaithTrack faithTrack;

    public FaithTrackMessage(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;

        super.setSenderUsername("SERVER_MESSAGE");
        super.setMessageType(PossibleMessages.FAITH_TRACK_MESSAGE);
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }
}
