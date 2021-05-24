package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.enumerations.PossibleMessages;
import it.polimi.ingsw.model.market.ProductionCard;
import it.polimi.ingsw.model.productionBoard.ProductionBoard;
import it.polimi.ingsw.model.productionBoard.ProductionSlot;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductionBoardMessage extends Message {

    private static final long serialVersionUID = 2682968447025057568L;
    HashMap<Integer, Integer> productionSlots;
    LeaderCardMessage extra_productions;

    public ProductionBoardMessage(ProductionBoard productionBoard) {
        super.setSenderUsername("SERVER_MESSAGE");
        super.setMessageType(PossibleMessages.PRODUCTION_BOARD);
        extra_productions = new LeaderCardMessage((List) productionBoard.getLeaderProductions());
        ProductionSlot[] slots = productionBoard.getProductionSlots();
        productionSlots = new HashMap<>();

        for (int i = 0; i < productionBoard.getProductionSlots().length; i++) {
            if(slots[i].getProductionCard() == null) productionSlots.put(i, -1);
            else productionSlots.put(i, slots[i].getProductionCard().getId());
        }

    }

    public LeaderCardMessage getExtra_productions() {
        return extra_productions;
    }

    public HashMap<Integer, Integer> getProductions() {
        return productionSlots;
    }
}
