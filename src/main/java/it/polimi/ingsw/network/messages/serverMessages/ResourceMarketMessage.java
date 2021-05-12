package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.network.messages.Message;

/**
 * Contains the current resource board. Is shown whenever resources are picked and
 * there needs to be an update.
 */
public class ResourceMarketMessage extends Message {

    private static final long serialVersionUID = -5118607182681649217L;

    private final ResourceType[][] resourceBoard;

    public ResourceMarketMessage(ResourceType[][] resourceBoard) {

        super.setMessageType(PossibleMessages.BOARD);
        this.resourceBoard = resourceBoard;
    }

    public ResourceType[][] getResourceBoard() {
        return resourceBoard;
    }
}
