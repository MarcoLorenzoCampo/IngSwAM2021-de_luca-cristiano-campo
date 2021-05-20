package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.MaterialResource;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

public class BufferMessage extends Message {
    private static final long serialVersionUID = 3226289351474499195L;

    private final ArrayList<ResourceType> buffer;

    public BufferMessage (ArrayList<MaterialResource> real_buffer){
        super.setSenderUsername("SERVER_MESSAGE");
        super.setMessageType(PossibleMessages.BUFFER);

        this.buffer = new ArrayList<>();
        for (MaterialResource iterator: real_buffer) {
            buffer.add(iterator.getResourceType());
        }
    }

    public ArrayList<ResourceType> getBuffer() {
        return buffer;
    }

    @Override
    public String toString() {
        return "BufferMessage{" +
                "buffer=" + buffer +
                '}';
    }
}
