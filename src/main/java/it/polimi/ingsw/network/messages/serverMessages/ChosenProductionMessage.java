package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.utilities.BaseProduction;
import it.polimi.ingsw.model.utilities.Resource;
import it.polimi.ingsw.model.utilities.ResourceTag;
import it.polimi.ingsw.network.messages.Message;

import java.util.HashMap;

public class ChosenProductionMessage extends Message {
    private static final long serialVersionUID = -5794161680047820236L;
    HashMap<ResourceType, Integer> input;
    HashMap<ResourceType, Integer> output;

    public ChosenProductionMessage(BaseProduction finalProduction) {
        super.setMessageType(PossibleMessages.FINAL_PRODUCTION);
        super.setSenderUsername("SERVER_MESSAGE");
        input = new HashMap<>();
        output = new HashMap<>();

        for (ResourceTag iterator : finalProduction.getInputResources()) {
            input.put(iterator.getType(), iterator.getQuantity());
        }

        for (ResourceTag iterator : finalProduction.getOutputResources()) {
            output.put(iterator.getType(), iterator.getQuantity());
        }
    }

    public HashMap<ResourceType, Integer> getOutput() {
        return output;
    }

    public HashMap<ResourceType, Integer> getInput() {
        return input;
    }
}
